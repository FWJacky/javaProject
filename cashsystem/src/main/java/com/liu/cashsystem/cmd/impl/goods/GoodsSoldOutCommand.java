package com.liu.cashsystem.cmd.impl.goods;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Goods;

/**
 * @ClassName GoodsSoldOutCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:44
 * @Version 1.0
 **/

@CommandMeta(name="XJSP",desc="下架商品",group = "商品信息")
@AdminCommand
public class GoodsSoldOutCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("下架商品");
        printlnInfo("请输入下架商品编号：");
        int goodsId = Integer.parseInt(scanner.next());
        Goods goods = this.goodsService.getGoods(goodsId);
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
