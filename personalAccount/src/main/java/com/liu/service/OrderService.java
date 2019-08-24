package com.liu.service;

import com.liu.dao.OrderDAO;
import com.liu.entity.Order;

import java.util.List;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:29
 * @Version 1.0
 **/
public class OrderService {

    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public List<Order> queryOrderByAccount(Integer id) {
        return this.orderDAO.queryOrderByAccount(id);
    }

    public boolean commitOrder(Order order) {
        return this.orderDAO.commitOrder(order);
    }
}
