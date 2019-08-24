package com.liu.common;

import lombok.Getter;
import lombok.ToString;

/**
 * @ClassName AccountStatus
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:31
 * @Version 1.0
 **/
@Getter
public enum  AccountStatus {

    UNLOCK(1,"启用"),LOCK(2,"停用");

    int flag;
    String desc;

    AccountStatus(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public static AccountStatus valueOf(int flag) {
        for (AccountStatus accountStatus : values()) {
            if (accountStatus.getFlag() == flag) {
                return accountStatus;
            }
        }
        throw new RuntimeException("accountStatus.flag "+flag+" not fount!");
    }
}
