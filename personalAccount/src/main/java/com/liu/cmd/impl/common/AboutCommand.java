package com.liu.cmd.impl.common;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.annotation.EntranceCommand;
import com.liu.cmd.impl.AbstractCommand;

/**
 * @ClassName AboutCommadn
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:35
 * @Version 1.0
 **/

@CommandMeta(name = "GYXT", desc = "关于系统",group = "公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class AboutCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("-----   订单管家   ------");
        printlnInfo("------  作者： 刘锋  -----");
        printlnInfo("------  2019-08-14  -----");
    }
}
