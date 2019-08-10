package com.liu.cashsystem.cmd.impl.goods;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Goods;

/**
 * @ClassName GoodsUpdateCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:45
 * @Version 1.0
 **/

@CommandMeta(name="GXSP",desc="更新商品",group = "商品信息")
@AdminCommand
public class GoodsUpdateCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("更新商品信息");
        printlnInfo("请输入更新商品的编号：");
        int goodsId = scanner.nextInt();
        Goods goods = this.goodsService.getGoods(goodsId);
        if(goods == null) {
            printlnInfo("没有此编号的货物");
            return;
        }else {
            printlnInfo("待更新的商品信息如下：");
            System.out.println(goods);
        }
        printlnInfo("请输入更新的商品简介：(若不修改此项请输入z)");
        String introduce = scanner.next();
        if("z".equalsIgnoreCase(introduce)) {
            introduce = goods.getIntroduce();
        }
        printlnInfo("请输入更新的商品库存：(若不修改此项请输入z)");
        String stockTemp = scanner.next();
        int stock = 0;
        if("z".equalsIgnoreCase(stockTemp)) {
            stock = goods.getStock();
        }else {
            stock = Integer.parseInt(stockTemp);
        }
        printlnInfo("请输入更新的商品单位：个，包，箱，台...(若不修改此项请输入z)");
        String unit = scanner.next();
        if("z".equalsIgnoreCase(unit)) {
            unit = goods.getUnit();
        }
        printlnInfo("请输入更新的商品价格：单位（元）");
        int price = new Double(100*scanner.nextDouble()).intValue();
        printlnInfo("请输入更新的商品折扣：75表示75折");
        int discount = Integer.parseInt(scanner.next());

        printlnInfo("请确认是否更新：y/n");
        String flag = scanner.next();
        if("y".equalsIgnoreCase(flag)) {
            // 更新数据库goods表的商品信息
            goods.setIntroduce(introduce);
            goods.setStock(stock);
            goods.setUnit(unit);
            goods.setPrice(price);
            goods.setDiscount(discount);
            boolean effect = this.goodsService.modifyGoods(goods);
            if(effect) {
                printlnInfo("商品更新成功");
            }else {
                printlnInfo("商品更新失败");
            }
        }else {
            printlnInfo("取消更新");
        }
    }
}
