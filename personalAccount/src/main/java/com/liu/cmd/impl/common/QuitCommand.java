package com.liu.cmd.impl.common;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.annotation.EntranceCommand;
import com.liu.cmd.impl.AbstractCommand;

/**
 * @ClassName QuitCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:36
 * @Version 1.0
 **/

@CommandMeta(name = "TCXT", desc = "退出系统",group = "公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class QuitCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("业务结束，退出系统！");
        scanner.close();
        System.exit(0);
    }
}
