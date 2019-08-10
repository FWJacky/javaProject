package com.liu.cashsystem.cmd.impl.common;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.annotation.EntranceCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;

/**
 * @ClassName AboutCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:43
 * @Version 1.0
 **/

@CommandMeta(name = "GYXT", desc = "关于系统", group = "公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class AboutCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("******  字符界面收银系统  ******");
        System.out.println("******    作者 刘锋      ******");
        System.out.println("******   2019-08-04     ******");
    }
}
