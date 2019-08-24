package com.liu.dao;

import com.liu.entity.Goods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GoodsDAO
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:30
 * @Version 1.0
 **/
public class GoodsDAO extends BaseDAO {

    private Connection conn = null;

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    // 浏览商品
    public List<Goods> queryAllGoods() {
        List<Goods> goodsList = new ArrayList<>();
        try {
            conn = this.getConnection(true);
            String sql = "select * from goods";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Goods goods = this.extractGoods(resultSet);
                if (goods != null) {
                    goodsList.add(goods);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return goodsList;
    }

    // 处理结果集
    private Goods extractGoods(ResultSet resultSet) throws SQLException {
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
            conn = this.getConnection(true);
            String sql = "insert into goods (name,introduce,stock,unit,price,discount) value (?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, goods.getName());
            preparedStatement.setString(2, goods.getIntroduce());
            preparedStatement.setInt(3, goods.getStock());
            preparedStatement.setString(4, goods.getUnit());
            preparedStatement.setInt(5, goods.getPrice());
            preparedStatement.setInt(6, goods.getDiscount());
            flag = (preparedStatement.executeUpdate() == 1);
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                goods.setId(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return flag;
    }

    // 通过商品编号查找商品
    public Goods queryGoodsById(int goodsId) {
        Goods goods = null;
        try {
            conn = this.getConnection(true);
            String sql = "select * from goods where id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, goodsId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                goods = this.extractGoods(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return goods;
    }

    // 下架商品
    public boolean soldOutGoods(int goodsId) {
        boolean effect = false;
        try {
            conn = this.getConnection(true);
            String sql = "delete from goods where id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, goodsId);
            effect = (preparedStatement.executeUpdate() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return effect;
    }

    // 更新商品
    public boolean modifyGoods(Goods goods) {
        boolean effect = false;
        try {
            conn = this.getConnection(true);
            String sql = "update goods set name=?,introduce=?,stock=?,unit=?,price=?,discount=? where id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, goods.getName());
            preparedStatement.setString(2, goods.getIntroduce());
            preparedStatement.setInt(3, goods.getStock());
            preparedStatement.setString(4, goods.getUnit());
            preparedStatement.setInt(5, goods.getPrice());
            preparedStatement.setInt(6, goods.getDiscount());
            preparedStatement.setInt(7, goods.getId());

            effect = (preparedStatement.executeUpdate() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return effect;
    }

    public Goods getGoods(int parseInt) {
        Goods goods = null;
        try {
            conn = this.getConnection(true);
            String sql = "select * from goods where id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, parseInt);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                goods = this.extractGoods(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return goods;
    }

    public boolean updateAfterPay(Goods goods, Integer buyGoodsNum) {
        boolean effect = false;
        try {
            conn = this.getConnection(true);
            String sql = "update goods set stock = ? where id = ?";
            preparedStatement = conn.prepareStatement(sql);
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
