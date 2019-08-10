package com.liu.cashsystem.dao;

import com.liu.cashsystem.entity.Goods;
import sun.plugin.dom.core.CoreConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GoodsDao
 * @Description TODO
 * @Author L
 * @Date 2019/8/6 15:48
 * @Version 1.0
 **/
public class GoodsDao extends BaseDao{

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    // 浏览商品
    public List<Goods> quarryAllGoods() {

        List<Goods> list = new ArrayList<>();
        try {
            connection = this.getConnection(true);
            String sql = "select * from goods";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Goods goods = this.extractGoods(resultSet);
                if(goods!=null) {
                    list.add(goods);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource(resultSet,preparedStatement,connection);
        }
        return list;
    }

    // 处理商品
    public Goods extractGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getInt("id"));
        goods.setName(resultSet.getString("name"));
        goods.setIntroduce(resultSet.getString("introduce"));
        goods.setStock(resultSet.getInt("stock"));
        goods.setUnit(resultSet.getString("unit"));
        goods.setPrice(resultSet.getInt("price"));
        goods.setDiscount(resultSet.getInt("discount"));
        return goods;
    }

    // 上架商品
    public boolean putAwayGoods(Goods goods) {

        boolean flag = false;
        try {
            connection = this.getConnection(true);
            String sql = "insert into goods (name,introduce,stock,unit,price,discount) value (?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,goods.getName());
            preparedStatement.setString(2,goods.getIntroduce());
            preparedStatement.setInt(3,goods.getStock());
            preparedStatement.setString(4,goods.getUnit());
            preparedStatement.setInt(5,goods.getPrice());
            preparedStatement.setInt(6,goods.getDiscount());
            flag = (preparedStatement.executeUpdate()==1);
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                goods.setId(resultSet.getInt(1));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource(resultSet,preparedStatement,connection);
        }
        return flag;
    }

    // 从数据库得到商品信息
    public Goods getGoods(int goodsId) {

        Goods goods = null;
        try {
            connection = this.getConnection(true);
            String sql = "select * from goods where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,goodsId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
               goods = this.extractGoods(resultSet);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource(resultSet,preparedStatement,connection);
        }
        return goods;
    }

    // 更新商品
    public boolean modifyGoods(Goods goods) {
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "update goods set name=?,introduce=?,stock=?,unit=?,price=?,discount=? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,goods.getName());
            preparedStatement.setString(2,goods.getIntroduce());
            preparedStatement.setInt(3,goods.getStock());
            preparedStatement.setString(4,goods.getUnit());
            preparedStatement.setInt(5,goods.getPrice());
            preparedStatement.setInt(6,goods.getDiscount());
            preparedStatement.setInt(7,goods.getId());

            effect = (preparedStatement.executeUpdate()==1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return effect;
    }

    // 下架商品
    public boolean soldOutGoods(int goodsId) {
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "delete from goods where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,goodsId);
            effect = (preparedStatement.executeUpdate()==1);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeResource(resultSet,preparedStatement,connection);
        }
        return effect;
    }

    // 购买商品后更新商品信息
    public boolean updateAfterPay(Goods goods, int buyGoodsNum) {
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "update goods set stock = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,goods.getStock()-buyGoodsNum);
            preparedStatement.setInt(2,goods.getId());
            if(preparedStatement.executeUpdate()==1) {
                effect = true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return effect;
    }
}
