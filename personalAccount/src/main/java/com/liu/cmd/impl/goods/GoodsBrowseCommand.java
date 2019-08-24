package com.liu.cmd.impl.goods;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Goods;

import java.util.List;

/**
 * @ClassName GoodsBrowseCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:36
 * @Version 1.0
 **/
@CommandMeta(name = "LLSP", desc = "浏览商品", group = "商品信息")
@AdminCommand
@CustomerCommand
public class GoodsBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("商品列表如下：");
        // 查看所有的商品
        List<Goods> goodsList = this.goodsService.queryAllGoods();
        if (goodsList == null) {
            printlnInfo("商品列表为空！");
        } else {
            for (Goods goods : goodsList) {
                printlnInfo("======================");
                System.out.println(goods);
            }
        }
    }
}
