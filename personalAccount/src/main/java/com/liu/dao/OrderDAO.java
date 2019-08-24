package com.liu.dao;

import com.liu.common.OrderStatus;
import com.liu.entity.Order;
import com.liu.entity.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderDAO
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:30
 * @Version 1.0
 **/
public class OrderDAO extends BaseDAO {

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // 查看订单
    public List<Order> queryOrderByAccount(Integer id) {
        List<Order> orderList = new ArrayList<>();
        try {
            conn = this.getConnection(false);
            // 多表联查
            String sql = this.getSql("@query_order_by_account");

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            Order order = null;
            while (resultSet.next()) {
                if (order == null) {
                    order = new Order();
                    // 解析订单
                    this.extractOrder(order, resultSet);
                    orderList.add(order);
                }

                // 想要拿到订单项所有内容，先要拿到当前的订单id
                String orderId = resultSet.getString("order_id");
                // 如果当前和之前的订单id不相同，则需要重新生成订单
                // 只有当订单信息不同的时候，我们才会生成一个订单
                // 订单对象只有一个，因为其中包含了很多的订单信息
                // 如果为每一个订单信息都生成一个订单不合理

                if (!orderId.equals(order.getId())) {
                    order = new Order();
                    this.extractOrder(order, resultSet);
                    orderList.add(order);
                }

                // 往当前订单添加具体的订单项
                OrderItem orderItem = this.extractOrderItem(resultSet);
                order.orderItemList.add(orderItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return orderList;
    }

    private OrderItem extractOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getInt("item_id"));
        orderItem.setGoods_id(resultSet.getInt("goods_id"));
        orderItem.setGoods_name(resultSet.getString("goods_name"));
        orderItem.setGoods_introduce(resultSet.getString("goods_introduce"));
        orderItem.setGoods_num(resultSet.getInt("goods_num"));
        orderItem.setGoods_unit(resultSet.getString("goods_unit"));
        orderItem.setGoods_price(resultSet.getInt("goods_price"));
        orderItem.setGoods_discount(resultSet.getInt("goods_discount"));
        return orderItem;
    }

    private void extractOrder(final Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getString("order_id"));
        order.setAccount_id(resultSet.getInt("account_id"));
        order.setAccount_name(resultSet.getString("account_name"));
        order.setCreate_time(resultSet.getTimestamp("create_time").toLocalDateTime());
        Timestamp finishTime = resultSet.getTimestamp("finish_time");
        if (finishTime != null) {
            order.setFinish_time(finishTime.toLocalDateTime());
        }
        order.setActual_amount(resultSet.getInt("actual_amount"));
        order.setTotal_money(resultSet.getInt("total_money"));
        order.setOrder_status(OrderStatus.valueOf(resultSet.getInt("order_status")));
    }

    // 提交订单
    public boolean commitOrder(Order order) {

        // 拿到连接
        try {
            // 手动提交事务
            conn = this.getConnection(false);
            String insertOrderSql = "insert into `order` (id,account_id,create_time,finish_time,actual_amount,total_money,order_status,account_name) values (?,?,now(),now(),?,?,?,?)";
            String insertOrderItemSql = "insert into order_item (order_id,goods_id,goods_name,goods_introduce,goods_num,goods_unit,goods_price,goods_discount) values (?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(insertOrderSql);
            preparedStatement.setString(1, order.getId());
            preparedStatement.setInt(2, order.getAccount_id());
            preparedStatement.setInt(3, order.getActual_amount());
            preparedStatement.setInt(4, order.getTotal_money());
            preparedStatement.setInt(5, order.getOrder_status().getFlag());
            preparedStatement.setString(6, order.getAccount_name());

            // 更新数据库
            if (preparedStatement.executeUpdate() == 0) {
                throw new RuntimeException("插入订单失败");
            }
            preparedStatement = conn.prepareStatement(insertOrderItemSql);
            for (OrderItem orderItem : order.orderItemList) {
                preparedStatement.setString(1, orderItem.getOrder_id());
                preparedStatement.setInt(2, orderItem.getGoods_id());
                preparedStatement.setString(3, orderItem.getGoods_name());
                preparedStatement.setString(4, orderItem.getGoods_introduce());
                preparedStatement.setInt(5, orderItem.getGoods_num());
                preparedStatement.setString(6, orderItem.getGoods_unit());
                preparedStatement.setInt(7, orderItem.getGoods_price());
                preparedStatement.setInt(8, orderItem.getGoods_discount());
                // 批量处理  将每一项preparedStatement缓存好
                preparedStatement.addBatch();
            }
            // 批量操作数据库
            int[] effects = preparedStatement.executeBatch();
            for (int i : effects) {
                if (i == 0) {
                    throw new RuntimeException("插入订单明细失败");
                }
            }

            // 手动提交事务
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            // 捕获到异常之后，将异常信息打印，并进行回滚
            if (conn != null) {
                try {
                    // 回滚
                    conn.rollback();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        } finally {
            this.closeResource(resultSet, preparedStatement, conn);
        }
        return true;
    }
}
