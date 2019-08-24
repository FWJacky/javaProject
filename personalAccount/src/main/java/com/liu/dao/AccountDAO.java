package com.liu.dao;

import com.liu.common.AccountStatus;
import com.liu.common.AccountType;
import com.liu.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AccountDAO
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:30
 * @Version 1.0
 **/
public class AccountDAO extends BaseDAO {

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // 根据username查询Account
    public Account getAccountByUsername(String username) {
        Account account = null;
        try {
            conn = this.getConnection(true);
            String sql = "select * from account where username = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = this.extractAccount(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return account;
    }

    // 处理resultSet
    private Account extractAccount(ResultSet resultSet) throws Exception {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setName(resultSet.getString("name"));
        account.setAccountType(AccountType.valueOf(resultSet.getInt("account_type")));
        account.setAccountStatus(AccountStatus.valueOf(resultSet.getInt("account_status")));
        return account;
    }


    // 登录
    public Account login(String username, String password) {
        Account account = null;
        try {
            conn = this.getConnection(true);
            String sql = "select * from account where username = ? and password = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, DigestUtils.md5Hex(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = this.extractAccount(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return account;
    }

    // 注册
    public boolean register(Account account) {
        boolean flag = false;
        try {
            conn = this.getConnection(true);
            String sql = "insert into account (username,password,name,account_type) value (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, DigestUtils.md5Hex(account.getPassword()));
            preparedStatement.setString(3, account.getName());
            preparedStatement.setInt(4, account.getAccountType().getFlag());
            flag = (preparedStatement.executeUpdate() == 1);
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                account.setId(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return flag;
    }


    // 重置密码
    public boolean modifyPassword(Account account) {
        boolean flag = false;
        try {
            conn = this.getConnection(true);
            String sql = "update account set password = ? where id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, DigestUtils.md5Hex(account.getPassword()));
            preparedStatement.setInt(2, account.getId());
            flag = (preparedStatement.executeUpdate() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    // 查看账户
    public List<Account> queryAllAccount() {
        List<Account> accountList = new ArrayList<>();
        try {
            conn = this.getConnection(true);
            String sql = "select * from account";
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = this.extractAccount(resultSet);
                if (account != null) {
                    accountList.add(account);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return accountList;
    }

    // 通过id查询账户
    public Account getAccountById(Integer id) {
        Account account = null;
        try {
            conn = this.getConnection(true);
            String sql = "select * from account where id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = this.extractAccount(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    // 启停账户
    public boolean setAccountStatus(Account account) {
        boolean flag = false;
        try {
            conn = this.getConnection(true);
            String sql = "update account set account_status=? where id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountStatus().getFlag());
            preparedStatement.setInt(2, account.getId());
            if (preparedStatement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, conn);
        }
        return flag;
    }

    // 重置密码
    public boolean resetPassword(Account account) {
        boolean flag = false;
        try {
            conn = this.getConnection(true);
            String sql = "update account set password = ? where id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, DigestUtils.md5Hex(account.getPassword()));
            preparedStatement.setInt(2, account.getId());
            if(preparedStatement.executeUpdate() == 1) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
