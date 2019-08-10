package com.liu.cashsystem.cmd.impl.entrance;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.EntranceCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.common.AccountStatus;
import com.liu.cashsystem.common.AccountType;
import com.liu.cashsystem.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName RegisterCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:44
 * @Version 1.0
 **/

@CommandMeta(name = "ZC", desc = "注册", group = "入口命令")
@EntranceCommand
public class RegisterCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        final Account account = new Account();
        System.out.println("输入用户名：");
        String username = scanner.nextLine();
        System.out.println("输入密码：");
        String password = scanner.nextLine();
        System.out.println("请确认密码：");
        String rePassword = scanner.nextLine();
        if (!rePassword.equals(password)) {
            System.out.println("两次输入的密码不一致，请重新注册");
            return;
        }
        System.out.println("请输入你的姓名：");
        String name = scanner.nextLine();
        System.out.println("请输入账户类型：（1代表管理员，2代表客户）");
        int account_type = scanner.nextInt();
        account.setUsername(username);
        account.setPassword(password);
        account.setName(name);
        account.setAccountType(AccountType.valueOf(account_type));
        boolean effect = this.accountService.register(account);
        if (effect) {
            System.out.println(account.getAccountType() + "注册成功");
        } else {
            System.out.println("注册失败");
        }

//        System.out.println("请输入用户名：");
//        String username = scanner.nextLine();
//        System.out.println("请输入密码：");
//        String password1 = scanner.nextLine();
//        System.out.println("请再次输入密码：");
//        String password2 = scanner.nextLine();
//        if (!password2.equals(password1)) {
//            System.out.println("两次输入的密码不一致！");
//            return;
//        }
//        System.out.println("请输入姓名：");
//        String name = scanner.nextLine();
//        System.out.println("请输入账户的类型：1-管理员，2-客户");
//        int accountType = scanner.nextInt();
//        AccountType accountType1 = AccountType.valueOf(accountType);
//
//        final Account account = new Account();
//        account.setUsername(username);
//        account.setPassword(password1);
//        account.setName(name);
//        account.setAccountType(accountType1);
//        boolean effect = this.accountService.register(account);
//        if (effect) {
//            System.out.println("注册成功");
//        } else {
//            System.out.println("注册失败");
//        }
    }
}
