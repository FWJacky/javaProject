package com.liu.service;

import com.liu.dao.AccountDAO;
import com.liu.entity.Account;
import com.liu.entity.Goods;

import java.util.List;

/**
 * @ClassName AccountService
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:29
 * @Version 1.0
 **/
public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account getAccountByUsername(String username) {
        return this.accountDAO.getAccountByUsername(username);
    }

    public Account login(String username, String password) {
        return this.accountDAO.login(username,password);
    }

    public boolean register(Account account) {
        return this.accountDAO.register(account);
    }

    public boolean modifyPassword(Account account) {
        return  this.accountDAO.modifyPassword(account);
    }

    public List<Account> queryAllAccount() {
        return this.accountDAO.queryAllAccount();
    }

    public Account getAccountById(Integer id) {
        return this.accountDAO.getAccountById(id);
    }

    public boolean setAccountStatus(Account account) {
        return this.accountDAO.setAccountStatus(account);
    }

    public boolean resetPassword(Account account) {
        return this.accountDAO.resetPassword(account);
    }
}
