package com.liu.entity;

import com.liu.common.AccountStatus;
import com.liu.common.AccountType;
import lombok.Data;

/**
 * @ClassName Account
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:28
 * @Version 1.0
 **/
@Data
public class Account {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private AccountType accountType;
    private AccountStatus accountStatus;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("【账户信息】:").append("\n")
                .append("【账号编号】").append(this.getId()).append("\n")
                .append("【用户名】").append(this.getUsername()).append("\n")
                .append("【用户密码】").append(this.getPassword()).append("\n")
                .append("【用户姓名】").append(this.getName()).append("\n")
                .append("【账户类型】").append(this.getAccountType()).append("\n")
                .append("【账号状态】").append(this.getAccountStatus()).append("\n");
        sb.append("======================================================");
        return sb.toString();
    }
}
