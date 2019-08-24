package com.liu.cmd.impl.account;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.CustomerCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.cmd.impl.common.QuitCommand;
import com.liu.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName AccountModifyPasswordCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 20:55
 * @Version 1.0
 **/
@CommandMeta(name = "XGMM", desc = "修改密码", group = "我的信息")
@CustomerCommand
@AdminCommand
public class AccountModifyPasswordCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("修改密码>>");
        printlnInfo("请输入原始密码：");
        String password = scanner.nextLine();
        Account account = this.accountService.getAccountByUsername(subject.getAccount().getUsername());
        if(!DigestUtils.md5Hex(password).equals(account.getPassword())) {
            printlnInfo("原始密码错误，请重新输入！");
            return;
        }
        printlnInfo("请输入新密码：");
        String newPassword = scanner.nextLine();
        printlnInfo("请再次输入新密码：");
        String rePassword = scanner.nextLine();
        if(!newPassword.equals(rePassword)) {
            printlnInfo("两次密码输入不一致，请重新输入！");
        }
        subject.getAccount().setPassword(newPassword);
        boolean flag = this.accountService.modifyPassword(subject.getAccount());
        if(flag) {
            printlnInfo("密码修改成功，请重新登录！");
            System.exit(0);
        }else {
            printlnInfo("密码修改失败！");
        }
    }
}
