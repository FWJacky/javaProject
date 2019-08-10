package com.liu.cashsystem.service;

import com.liu.cashsystem.dao.AccountDao;
import com.liu.cashsystem.entity.Account;

import java.util.List;

/**
 * @ClassName AccountService
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 16:28
 * @Version 1.0
 **/

public class AccountService {

    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    // 登录
    public Account login(String username, String password) {
        return this.accountDao.login(username, password);
    }

    // 注册
    public boolean register(Account account) {
        return this.accountDao.register(account);
    }

    // 重置密码
    public boolean resetPassword(Account account) {
        return this.accountDao.resetPassword(account);
    }

    // 启停账户
    public boolean setAccountStatus(Account account) {
        return this.accountDao.setAccountStatus(account);
    }

    // 查看账户
    public List<Account> queryAllAccount() {
        return this.accountDao.queryAllAccount();
    }

    // 从数据库得到账户信息
    public Account getAccount(int account_id) {
        return this.accountDao.getAccount(account_id);
    }

    // 登录时校验用户名及密码是否正确
    public Account getAccountByUsername(String username) {
        return this.accountDao.getAccountByUsername(username);
    }
}
