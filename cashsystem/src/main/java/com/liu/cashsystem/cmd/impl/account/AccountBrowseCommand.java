package com.liu.cashsystem.cmd.impl.account;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AccountBrowseCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:42
 * @Version 1.0
 **/
@CommandMeta(name="CKZH",desc="查看账户",group = "账号信息")
@AdminCommand
public class AccountBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("查看账户：");
        // 查看所有的账户
        List<Account> accountList  = this.accountService.queryAllAccount();
        if(accountList.isEmpty()) {
            printlnInfo("没有账户");
        }else {
            for (Account account : accountList) {
                System.out.println(account);
            }
        }
    }
}
