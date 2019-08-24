package com.liu.cmd;

import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Account;

import java.util.Scanner;

/**
 * @ClassName CheckStand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:27
 * @Version 1.0
 **/
public class CheckStand extends AbstractCommand {


    @Override
    public void execute(Subject subject) {
        // 首先提供帮助信息
        Commands.getCachedHelpCommands().execute(subject);

        while (true) {
            printlnInfo(">>>");
            // dl命令
            String line = scanner.nextLine();
            String commandCode = line.trim().toUpperCase();
            Account account = subject.getAccount();
            if(account == null) {
                Commands.getEntranceCommand(commandCode).execute(subject);
            }else {
                switch (account.getAccountType()) {
                    case ADMIN:
                        Commands.getAdminCommand(commandCode).execute(subject);
                        break;
                    case CUSTOMER:
                        Commands.getCustomerCommand(commandCode).execute(subject);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Subject subject = new Subject();
        new CheckStand().execute(subject);
    }
}
