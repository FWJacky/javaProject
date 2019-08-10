package com.liu.cashsystem.cmd;

import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Account;

/**
 * @ClassName CheckStand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 15:03
 * @Version 1.0
 **/
public class CheckStand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        // 帮助信息
        Commands.getCachedHelpCommands().execute(subject);
        while (true) {
            System.out.println(">>>");
            // dl
            String line = scanner.nextLine();
            String commandCode = line.trim().toUpperCase();
            Account account = subject.getAccount();
            if(account == null) {
                Commands.getEntranceCommand(commandCode).execute(subject);
                // TODO  在注册之后显示所在客户端还是管理端
//                execute(subject);
            }else {
                switch (account.getAccountType()) {
                    case ADMIN:
                        Commands.getAdminCommand(commandCode).execute(subject);
                        break;
                    case CUSTOMER:
                        Commands.getCustomerCommand(commandCode).execute(subject);
                        break;
                    default:
                }
            }
        }
    }

    public static void main(String[] args) {
        Subject subject = new Subject();
        new CheckStand().execute(subject);
    }
}
