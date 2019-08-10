package com.liu.cashsystem.cmd.impl.goods;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Goods;

import java.util.List;

/**
 * @ClassName GoodsBrowseCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:44
 * @Version 1.0
 **/

@CommandMeta(name="LLSP",desc="浏览商品",group = "商品信息")
@AdminCommand
@CustomerCommand
public class GoodsBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("浏览商品");
        // 1. 查询所有商品
        List<Goods> goodsList = this.goodsService.quarryAllGoods();
        if(goodsList.isEmpty()) {
            System.out.println("商品为空");
        }else {
            for (Goods goods : goodsList) {
                printlnInfo("======================");
                System.out.println(goods);
            }
        }
    }
}
