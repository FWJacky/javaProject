package com.liu.cashsystem.cmd.impl.account;

import com.liu.cashsystem.cmd.Subject;
import com.liu.cashsystem.cmd.annotation.AdminCommand;
import com.liu.cashsystem.cmd.annotation.CommandMeta;
import com.liu.cashsystem.cmd.impl.AbstractCommand;
import com.liu.cashsystem.common.AccountStatus;
import com.liu.cashsystem.entity.Account;

/**
 * @ClassName AccountStatusSetCommand
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 11:43
 * @Version 1.0
 **/

@CommandMeta(name="QTZH",desc="启停账号",group = "账号信息")
@AdminCommand
public class AccountStatusSetCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        printlnInfo("启停账户>>");
        printlnInfo("请输入待启停账户的id：");
        Integer account_id = scanner.nextInt();
        Account account = this.accountService.getAccount(account_id);
        if(account==null) {
            printlnInfo("此账户不存在");
            return;
        }else {
            printlnInfo("待启停账户信息如下：");
            System.out.println(account);
        }
        printlnInfo("请选择启用账户或者停用账户：1代表启用，2代表停用");
        Integer account_Status = scanner.nextInt();
        printlnInfo("请确认是否启停该账户：y/n");
        String confirm = scanner.next();
        if("y".equalsIgnoreCase(confirm)) {
            account.setAccountStatus(AccountStatus.valueOf(account_Status));
            boolean effect = this.accountService.setAccountStatus(account);
            if (effect) {
                printlnInfo("该账户启停操作成功");
            }else {
                printlnInfo("该账户启停操作失败");
            }
        }else {
            printlnInfo("已取消对该账户的启停操作！");
        }
    }
}
