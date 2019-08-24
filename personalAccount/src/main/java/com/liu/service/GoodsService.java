package com.liu.service;

import com.liu.dao.GoodsDAO;
import com.liu.entity.Goods;

import java.util.List;

/**
 * @ClassName GoodsService
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:29
 * @Version 1.0
 **/
public class GoodsService {

    private GoodsDAO goodsDAO;

    public GoodsService() {
        this.goodsDAO = new GoodsDAO();
    }

    public List<Goods> queryAllGoods() {
        return this.goodsDAO.queryAllGoods();
    }

    public boolean putAwayGoods(Goods goods) {
        return this.goodsDAO.putAwayGoods(goods);
    }

    public Goods queryGoodsById(int goodsId) {
        return this.goodsDAO.queryGoodsById(goodsId);
    }

    public boolean soldOutGoods(int goodsId) {
        return this.goodsDAO.soldOutGoods(goodsId);
    }

    public boolean modifyGoods(Goods goods) {
        return this.goodsDAO.modifyGoods(goods);
    }

    public Goods getGoods(int parseInt) {
        return this.goodsDAO.getGoods(parseInt);
    }

    public boolean updateAfterPay(Goods goods, Integer buyGoodsNum) {
        return this.goodsDAO.updateAfterPay(goods,buyGoodsNum);
    }
}
