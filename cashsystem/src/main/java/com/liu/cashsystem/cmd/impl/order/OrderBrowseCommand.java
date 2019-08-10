package com.liu.cashsystem.cmd.impl.order;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Order;

import java.util.List;

/**
 * @ClassName OrderBrowseCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:45
 * @Version 1.0
 **/

@CommandMeta(name="LLDD",desc="浏览订单",group = "订单信息")
@CustomerCommand
public class OrderBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("我的订单列表：");
        List<Order> orderList = this.orderService.queryOrderByAccount(subject.getAccount().getId());
        if(orderList.isEmpty()) {
            printlnInfo("没有订单");
        }else {
            for (Order order : orderList) {
                printlnInfo("----------------------------------");
                System.out.println(order);
                printlnInfo("----------------------------------");
            }
        }
    }
}
