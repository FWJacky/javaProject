package com.liu.common;

import lombok.Getter;
import lombok.ToString;

/**
 * @ClassName AccountType
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:31
 * @Version 1.0
 **/
@Getter
public enum AccountType {
    ADMIN(1,"管理员"),CUSTOMER(2,"客户");

    private int flag;
    private String desc;

    AccountType(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public static AccountType valueOf(int flag) {
        for (AccountType accountType : values()) {
            if(accountType.getFlag() == flag) {
                return accountType;
            }
        }
        throw new RuntimeException("accountType.flag " + flag +" not found!");
    }
}
