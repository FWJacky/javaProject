package com.liu.cmd.impl.entrance;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.annotation.EntranceCommand;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.common.AccountStatus;
import com.liu.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName LoginCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:36
 * @Version 1.0
 **/
@CommandMeta(name = "DL", desc = "登录", group = "入口命令")
@EntranceCommand
public class LoginCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        if (account != null) {
            printlnInfo("您已经登录过了。");
        }
        printlnInfo("请输入用户名：");
        String username = scanner.nextLine();
        // 首先判断用户名是否存在
        Account localAccount = this.accountService.getAccountByUsername(username);
        printlnInfo("请输入密码：");
        String password = scanner.nextLine();
        if (localAccount == null) {
            printlnInfo("用户名不存在，请输入正确的用户名！");
            return;
        } else {
            if (!DigestUtils.md5Hex(password).equals(localAccount.getPassword())) {
                printlnInfo("密码错误，请重新输入密码！");
                return;
            }
        }
        account = this.accountService.login(username, password);
        if (account != null && account.getAccountStatus() == AccountStatus.UNLOCK) {
            printlnInfo(account.getAccountType() + "登录成功！");
            subject.setAccount(account);
        }else if(account.getAccountStatus() == AccountStatus.LOCK){
            printlnInfo("账户已被停用，请联系管理员启用该账户！");
        }
    }
}
