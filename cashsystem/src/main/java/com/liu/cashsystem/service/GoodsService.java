package com.liu.cashsystem.service;

import com.liu.cashsystem.dao.GoodsDao;
import com.liu.cashsystem.entity.Goods;

import java.util.List;

/**
 * @ClassName GoodsService
 * @Description TODO
 * @Author L
 * @Date 2019/8/6 15:45
 * @Version 1.0
 **/
public class GoodsService {

    public GoodsDao goodsDao;

    public GoodsService() {
        this.goodsDao = new GoodsDao();
    }

    // 浏览商品
    public List<Goods> quarryAllGoods() {
        return this.goodsDao.quarryAllGoods();
    }

    // 上架商品
    public boolean putAwayGoods(Goods goods) {
        return this.goodsDao.putAwayGoods(goods);
    }

    // 从数据库获得商品信息
    public Goods getGoods(int goodsId) {
        return this.goodsDao.getGoods(goodsId);
    }

    // 更新商品
    public boolean modifyGoods(Goods goods) {
        return this.goodsDao.modifyGoods(goods);
    }

    public boolean soldOutGoods(int goodsId) {
        return this.goodsDao.soldOutGoods(goodsId);
    }

    public boolean updateAfterPay(Goods goods, int buyGoodsNum) {
        return this.goodsDao.updateAfterPay(goods,buyGoodsNum);
    }
}
