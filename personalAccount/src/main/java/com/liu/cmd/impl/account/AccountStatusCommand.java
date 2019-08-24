package com.liu.cmd.impl.account;

import com.liu.cmd.Subject;
import com.liu.cmd.annotation.AdminCommand;
import com.liu.cmd.annotation.CommandMeta;
import com.liu.cmd.impl.AbstractCommand;
import com.liu.common.AccountStatus;
import com.liu.entity.Account;

import java.util.List;

/**
 * @ClassName AccountStatusCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:35
 * @Version 1.0
 **/

@CommandMeta(name = "QTZH", desc = "启停账户", group = "账号信息")
@AdminCommand
public class AccountStatusCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("启停账户>>");
        printlnInfo("账号列表如下：");
        List<Account> accountList = this.accountService.queryAllAccount();
        for (Account account : accountList) {
            System.out.println(account);
        }
        printlnInfo("请输入你需要启停的账户编号：");
        Integer id = scanner.nextInt();
        Account account = this.accountService.getAccountById(id);
        if (account == null) {
            printlnInfo("此账号不存在");
            return;
        } else {
            printlnInfo("待启停的账号信息如下");
            System.out.println(account);
        }
        printlnInfo("请选择启用账户或停用账户：1 启用  2 停用");
        Integer account_status = scanner.nextInt();
        printlnInfo("请确认是否启停该账户：y/n");
        String confirm = scanner.next();
        if ("y".equalsIgnoreCase(confirm)) {
            account.setAccountStatus(AccountStatus.valueOf(account_status));
            boolean flag = this.accountService.setAccountStatus(account);
            if (flag) {
                printlnInfo("账户启停操作成功！");
            }else {
                printlnInfo("账户启停操作失败！");
            }
        }else {
            printlnInfo("已取消对该账户的启停操作！");
        }
    }
}
