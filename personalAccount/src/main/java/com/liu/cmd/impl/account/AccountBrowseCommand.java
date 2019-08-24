package com.liu.cmd.impl.account;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.entity.Account;

import java.util.List;

/**
 * @ClassName AccountBrowseCommand
 * @Description TODO    查看用户
 * @Author L
 * @Date 2019/8/22 15:34
 * @Version 1.0
 **/
@CommandMeta(name="CKZH",desc = "查看账户",group = "账号信息")
@AdminCommand
public class AccountBrowseCommand extends AbstractCommand {

    @Override
    public void execute(Subject subject) {
        printlnInfo("账号列表如下：");
        List<Account> accountList = this.accountService.queryAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
    }
}
