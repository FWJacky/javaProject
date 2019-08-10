package com.liu.cashsystem.cmd.impl.common;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.annotation.EntranceCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;

/**
 * @ClassName QuitCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:43
 * @Version 1.0
 **/

@CommandMeta(name="TCXT",desc="退出系统",group = "公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class QuitCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("业务完成，退出系统");
        scanner.close();
        System.exit(0);
    }
}
