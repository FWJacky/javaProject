package com.liu.cashsystem.cmd.impl.account;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName AccountPasswordResetCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:43
 * @Version 1.0
 **/

@CommandMeta(name="CZMM",desc="重置密码",group = "账号信息")
@AdminCommand
public class AccountPasswordResetCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("重置账户密码>>");
        printlnInfo("请输入待重置密码的账户id：");
        Integer accountId = scanner.nextInt();
        Account account = this.accountService.getAccount(accountId);
        if(account==null) {
            printlnInfo("此账户不存在");
            return;
        }else {
            printlnInfo("待重置密码的账户信息如下：");
            System.out.println(account);
        }
        printlnInfo("请输入新的密码：");
        String password = scanner.next();
        printlnInfo("请再次输入新的密码：");
        String rePassword = scanner.next();
        if(!password.equals(rePassword)) {
            printlnInfo("两次输入的密码不一致，请重新输入！");
        }
        printlnInfo("请确认是否重置账户密码：y/n");
        String confirm = scanner.next();
        if("y".equalsIgnoreCase(confirm)) {
            account.setPassword(password);
            boolean flag = this.accountService.resetPassword(account);
            if (flag) {
                printlnInfo("密码重置成功");
            } else {
                printlnInfo("密码重置失败");
            }
        }else {
            printlnInfo("已取消重置该账户密码！");
        }
    }
}
