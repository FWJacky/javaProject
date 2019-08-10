package com.liu.cashsystem.cmd.impl;

import com.liu.cashsystem.cmd.Command;
import com.liu.cashsystem.service.AccountService;
import com.liu.cashsystem.service.GoodsService;
import com.liu.cashsystem.service.OrderService;

/**
 * @ClassName AbstractCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:53
 * @Version 1.0
 **/
public abstract class AbstractCommand implements Command {
    // 启动所有的服务
    public AccountService accountService;

    public GoodsService goodsService;

    public OrderService orderService;

    public AbstractCommand() {
        this.accountService = new AccountService();
        this.goodsService = new GoodsService();
        this.orderService = new OrderService();
    }

    public void printlnInfo(String str) {
        System.out.println(str);
    }
}
