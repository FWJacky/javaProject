package com.liu.cmd.impl;

import com.liu.cmd.Command;
import com.liu.service.AccountService;
import com.liu.service.GoodsService;
import com.liu.service.OrderService;

/**
 * @ClassName AbstractCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:23
 * @Version 1.0
 **/
public abstract class AbstractCommand implements Command {

    // 启动所有服务
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
