package com.liu.cashsystem.cmd.impl.common;

import com.liu.cashsystem.cmd.Command;
import com.liu.cashsystem.cmd.Commands;
import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.CustomerCommand;
import com.liu.cashsystem.cmd.annotation.EntranceCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Account;

import java.util.*;

/**
 * @ClassName HelpCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:43
 * @Version 1.0
 **/

@CommandMeta(name = "BZXX", desc = "帮助信息", group = "公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class HelpCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        // 程序刚运行，account为空
        if (account == null) {
            entranceHelp();
        } else {
            switch (account.getAccountType()) {
                case ADMIN:
                    adminHelp();
                    break;
                case CUSTOMER:
                    customerHelp();
                    break;
                default:
            }
        }
    }

    public void adminHelp() {
        print("管理端", Commands.ADMIN_COMMANDS.values());
    }

    public void customerHelp() {
        print("客户端", Commands.CUSTOMER_COMMANDS.values());
    }

    // map.values()方法，会返回所有Value的集合
    public void entranceHelp() {
        print("欢迎", Commands.ENTRANCE_COMMANDS.values());
    }

    // 通用的打印命令
    public void print(String title, Collection<Command> collection) {
        System.out.println("*************" + title + "*************");

        // 将CommandMeta中的group和desc+name组成一个map
        Map<String, List<String>> helpInfo = new HashMap<>();
        for (Command command : collection) {
            CommandMeta commandMeta = command.getClass().getDeclaredAnnotation(CommandMeta.class);
            String group = commandMeta.group(); // 新的map的key值

            // 第一次没有group对应的List时，此时List为空
            List<String> func = helpInfo.computeIfAbsent(group, k -> new ArrayList<>());

            func.add(commandMeta.desc() + "(" + commandMeta.name() + ")");
        }

        int i = 0;
        // entrySet()：取出键值对的集合  getKey() getValue()
        for (Map.Entry<String, List<String>> entry : helpInfo.entrySet()) {
            i++;
            System.out.println(i + "." + entry.getKey());
            int j = 0;
            for (String item : entry.getValue()) {
                j++;
                System.out.println("\t" + (i) + "." + (j) +" "+ item);
            }
        }
        System.out.println("输入菜单括号后的编号(忽略大小写)，进行下一步操作");
        System.out.println("********************************************");
    }
}
