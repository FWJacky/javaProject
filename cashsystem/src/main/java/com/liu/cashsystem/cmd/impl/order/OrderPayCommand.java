package com.liu.cashsystem.cmd.impl.order;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.common.OrderStatus;
import com.liu.cashsystem.entity.Goods;
import com.liu.cashsystem.entity.Order;
import com.liu.cashsystem.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderPayCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:45
 * @Version 1.0
 **/

@CommandMeta(name = "ZFDD", desc = "支付订单", group = "订单信息")
@CustomerCommand
public class OrderPayCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("请输入你要购买的货物id以及数量，多个货物之间使用,号隔开：格式：1-8,2-5 || 若不够买请按回车退出");
        String string = scanner.nextLine();
        if("".equals(string)) {
            printlnInfo("已取消购买，退出此业务！");
            return;
        }
        // [0] = "1-8"  [1] = "2-5"
        String[] strings = string.split(",");
        // 把所有需要购买的商品存放至goodsList
        List<Goods> goodsList = new ArrayList<>();
        for (String goodString : strings) {
            // [0] = "1"   [1] = "8"
            String[] str = goodString.split("-");
            // 通过goods_id得到Goods
            Goods goods = this.goodsService.getGoods(Integer.parseInt(str[0]));
            goods.setBuyGoodsNum(Integer.parseInt(str[1]));
            goodsList.add(goods);
        }

        // 生成订单 ---- 在支付成功时插入
        Order order = new Order();
        order.setId(String.valueOf(System.currentTimeMillis()));
        order.setAccount_id(subject.getAccount().getId());
        order.setAccount_name(subject.getAccount().getName());
        order.setCreate_time(LocalDateTime.now());

        // 计算支付金额
        int totalMoney = 0;
        int actualMoney = 0;
        for (Goods goods : goodsList) {
            // 订单项
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder_id(order.getId());
            orderItem.setGoods_id(goods.getId());
            orderItem.setGoods_num(goods.getBuyGoodsNum());
            orderItem.setGoods_name(goods.getName());
            orderItem.setGoods_introduce(goods.getIntroduce());
            orderItem.setGoods_unit(goods.getUnit());
            orderItem.setGoods_price(goods.getPrice());
            orderItem.setGoods_discount(goods.getDiscount());

            // 将订单项放入订单中
            order.orderItemList.add(orderItem);

            int currentMoney = goods.getBuyGoodsNum() * goods.getPrice();
            // 实际所花的金额，即没有折扣
            totalMoney += currentMoney;
            // 打折后的金额
            actualMoney += currentMoney * goods.getDiscount() / 100;
        }
        order.setTotal_money(totalMoney);
        order.setActual_amount(actualMoney);
        // 当前订单状态为正在支付状态
        order.setOrder_status(OrderStatus.PLAYING);

        // 支付前先打印订单信息
        System.out.println(order);
        printlnInfo("请输入是否支付订单：zf");
        String confirm = scanner.next();

        if("zf".equalsIgnoreCase(confirm)) {

            // 支付成功时，订单需要插入订单完成时间
            order.setFinish_time(LocalDateTime.now());
            // 订单状态修改为支付完成
            order.setOrder_status(OrderStatus.OK);

            boolean effect = this.orderService.commitOrder(order);

            if(effect) {
                // 说明插入订单和订单项成功
                // 此时需要更新商品表
                for (Goods goods : goodsList) {
                    boolean isUpdate = this.goodsService.updateAfterPay(goods,goods.getBuyGoodsNum());
                    if(isUpdate) {
                        printlnInfo(goods.getName()+" 库存更新成功");
                    }else {
                        printlnInfo(goods.getName()+" 库存更新失败");
                    }
                }
            }

        }else {
            printlnInfo("订单支付失败，请重新下单！");
        }
    }
}
