package com.liu.cashsystem.cmd.impl.entrance;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.annotation.EntranceCommand;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.common.AccountStatus;
import com.liu.cashsystem.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName LoginCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:43
 * @Version 1.0
 **/

@CommandMeta(name="DL",desc="登录",group = "入口命令")
@EntranceCommand
public class LoginCommand extends AbstractCommand {

    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        if(account!=null) {
            System.out.println("您已经登录过了");
        }
        System.out.println("输入用户名：");
        String username = scanner.nextLine();
        Account localAccount = this.accountService.getAccountByUsername(username);
        System.out.println("输入密码：");
        String password = scanner.nextLine();
        if(localAccount == null) {
            printlnInfo("此用户名不存在，请输入正确的用户名！");
            return;
        }else {
            if(!DigestUtils.md5Hex(password).equals(localAccount.getPassword())) {
                printlnInfo("密码错误，请重新输入密码！");
                return;
            }
        }
        account = this.accountService.login(username,password);
        if(account!=null && account.getAccountStatus() == AccountStatus.UNLOCK) {
            System.out.println(account.getAccountType() + "登录成功");
            subject.setAccount(account);
        }else if(account.getAccountStatus() == AccountStatus.LOCK){
            printlnInfo("此账户处于停用状态，请联系管理员启用该账户！");
        }
    }
}
