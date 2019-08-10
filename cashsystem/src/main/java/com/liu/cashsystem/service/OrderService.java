package com.liu.cashsystem.service;

import com.liu.cashsystem.dao.OrderDao;
import com.liu.cashsystem.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author L
 * @Date 2019/8/10 15:42
 * @Version 1.0
 **/
public class OrderService {

    private OrderDao orderDao;

    public OrderService() {
        this.orderDao = new OrderDao();
    }

    public boolean commitOrder(Order order) {
        return this.orderDao.commitOrder(order);
    }

    public List<Order> queryOrderByAccount(Integer accountId) {
        return this.orderDao.queryOrderByAccount(accountId);
    }
}
