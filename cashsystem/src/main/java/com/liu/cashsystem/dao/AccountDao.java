package com.liu.cashsystem.dao;

import com.liu.cashsystem.common.AccountStatus;
import com.liu.cashsystem.common.AccountType;
import com.liu.cashsystem.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AccountDao
 * @Description TODO
 * @Author L
 * @Date 2019/8/4 16:30
 * @Version 1.0
 **/
public class AccountDao extends BaseDao {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // 操作数组库  执行登录
    public Account login(String username, String password) {

        Account account = null;

        try {
            // 拿到连接
            connection = this.getConnection(true);
            String sql = "select id,username,password,name,account_type,account_status from account where username = ? and password = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, DigestUtils.md5Hex(password));

            resultSet = preparedStatement.executeQuery();
            // 返回结果集到resultSet
            if (resultSet.next()) {
                // 解析resultSet，拿到对应的account
                account = this.extractAccount(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, connection);
        }
        return account;
    }

    public Account extractAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setName(resultSet.getString("name"));
        account.setAccountType(AccountType.valueOf(resultSet.getInt("account_type")));
        account.setAccountStatus(AccountStatus.valueOf(resultSet.getInt("account_status")));
        return account;
    }

    // 注册
    public boolean register(Account account) {

        boolean flag = false;
        try {
            connection = this.getConnection(true);
            String sql = "select id,username,password,name,account_type,account_status from account where username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // 这里说明数据库中已经存在了相同的用户名
                return false;
            }
            String registerSql = "insert into account (username,password,name,account_type) value (?,?,?,?)";
            preparedStatement = connection.prepareStatement(registerSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, DigestUtils.md5Hex(account.getPassword()));
            preparedStatement.setString(3, account.getName());
            preparedStatement.setInt(4, account.getAccountType().getFlag());
            flag = (preparedStatement.executeUpdate() == 1);
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                account.setId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, connection);
        }
        return flag;
    }

    // 查看账户
    public List<Account> queryAllAccount() {
        List<Account> accountList = new ArrayList<>();
        try {
            connection = this.getConnection(true);
            String sql = "select * from account";
            preparedStatement = connection.prepareStatement(sql);
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
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return accountList;
    }

    // 重置密码
    public boolean resetPassword(Account account) {
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "update account set password = ? where id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, DigestUtils.md5Hex(account.getPassword()));
            preparedStatement.setInt(2, account.getId());
            if (preparedStatement.executeUpdate() == 1) {
                effect = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return effect;
    }

    // 启停账户
    public boolean setAccountStatus(Account account) {
        boolean effect = false;

        try {
            connection = this.getConnection(true);
            String sql = "update account set account_status = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountStatus().getFlag());
            preparedStatement.setInt(2, account.getId());
            if (preparedStatement.executeUpdate() == 1) {
                effect = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return effect;
    }

    // 从数据库得到账户信息
    public Account getAccount(int account_id) {
        Account account = null;
        try {
            connection = this.getConnection(true);
            String sql = "select * from account where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = this.extractAccount(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, connection);
        }
        return account;
    }

    // 登录时校验用户名及密码是否正确
    public Account getAccountByUsername(String username) {
        Account account = null;
        try {
            connection = this.getConnection(true);
            String sql = "select * from account where username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = this.extractAccount(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(resultSet, preparedStatement, connection);
        }
        return account;
    }


}
