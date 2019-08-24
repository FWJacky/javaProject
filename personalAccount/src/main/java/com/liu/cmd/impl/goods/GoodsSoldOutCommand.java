package com.liu.cmd.impl.goods;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Goods;

import java.util.List;

/**
 * @ClassName GoodsSoldOutCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:37
 * @Version 1.0
 **/

@CommandMeta(name = "XJSP", desc = "下架商品", group = "商品信息")
@AdminCommand
public class GoodsSoldOutCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("下架商品>>");
        printlnInfo("商品列表如下：");
        List<Goods> goodsList = this.goodsService.queryAllGoods();
        for (Goods goods : goodsList) {
            System.out.println(goods);
        }
        printlnInfo("请输入要下架的商品编号：");
        int goodsId = Integer.parseInt(scanner.next());
        Goods goods = this.goodsService.queryGoodsById(goodsId);
        if(goods == null) {
            printlnInfo("没有此编号的货物");
            return;
        }else {
            printlnInfo("下架商品信息如下：");
            System.out.println(goods);
        }
        printlnInfo("确认是否下架：y/n");
        String confirm = scanner.next();
        if("y".equalsIgnoreCase(confirm)) {
            boolean effect = this.goodsService.soldOutGoods(goodsId);
            if(effect) {
                printlnInfo("商品成功下架");
            }else {
                printlnInfo("商品下架失败，稍后重试");
            }
        }else {
            printlnInfo("放弃下架商品");
        }
    }
}
