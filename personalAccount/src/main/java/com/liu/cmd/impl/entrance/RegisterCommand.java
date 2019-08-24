package com.liu.cmd.impl.entrance;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.EntranceCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.common.AccountType;
import com.liu.entity.Account;

/**
 * @ClassName RegisterCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:36
 * @Version 1.0
 **/

@CommandMeta(name = "ZC", desc = "注册", group = "入口命令")
@EntranceCommand
public class RegisterCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = new Account();
        printlnInfo("请输入用户名：");
        String username = scanner.nextLine();
        Account account1 = this.accountService.getAccountByUsername(username);
        if(account1!=null) {
            printlnInfo("该用户名已被注册！");
            return;
        }
        printlnInfo("请输入密码：");
        String password = scanner.nextLine();
        printlnInfo("请再次输入密码：");
        String repassword = scanner.nextLine();
        if (!password.equals(repassword)) {
            printlnInfo("密码不一致，请重新注册！");
            return;
        }
        printlnInfo("请输入你的姓名：");
        String name = scanner.nextLine();
        printlnInfo("请输入账户类型：1 管理员  2 客户");
        Integer accountType = scanner.nextInt();
        account.setUsername(username);
        account.setPassword(password);
        account.setName(name);
        account.setAccountType(AccountType.valueOf(accountType));
        boolean flag = this.accountService.register(account);
        if (flag) {
            printlnInfo(account.getAccountType() + "登录成功！");
        } else {
            printlnInfo("Sorry，注册失败，请重新注册！");
        }
    }
}
