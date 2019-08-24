package com.liu.cmd.impl.account;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Account;

/**
 * @ClassName AccountInfoCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 20:56
 * @Version 1.0
 **/

@CommandMeta(name = "GRXX",desc = "个人信息",group = "我的信息")
@CustomerCommand
@AdminCommand
public class AccountInfoCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("个人信息如下：");
        Account account = subject.getAccount();
        Account account1 = this.accountService.getAccountByUsername(account.getUsername());
        System.out.println(account1);
    }
}
