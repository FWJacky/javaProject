package com.liu.cashsystem.entity;

import lombok.Data;

/**
 * @ClassName OrderItem
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 10:41
 * @Version 1.0
 **/

@Data
public class OrderItem {

    private Integer id;
    private String order_id;
    private Integer goods_id;
    private String goods_name;
    private String goods_introduce;
    private Integer goods_num;
    private String goods_unit;
    private Integer goods_price;
    private Integer goods_discount;
}
