package com.liu.cmd.impl.order;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Order;

import java.util.List;

/**
 * @ClassName OrderBrowseCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:37
 * @Version 1.0
 **/
@CommandMeta(name = "CKDD", desc = "查看订单", group = "我的订单")
@CustomerCommand
public class OrderBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("我的订单列表：");
        List<Order> orderList = this.orderService.queryOrderByAccount(subject.getAccount().getId());
        if (orderList == null) {
            printlnInfo("没有订单。");
        } else {
            for (Order order : orderList) {
                printlnInfo("----------------------------------");
                System.out.println(order);
                printlnInfo("----------------------------------");
            }
        }
    }
}
