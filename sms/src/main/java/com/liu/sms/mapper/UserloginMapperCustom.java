package com.liu.sms.mapper;

import com.liu.sms.po.Userlogin;
import com.liu.sms.po.UserloginCustom;

/**
 *  UserloginMapper扩展类
 */
public interface UserloginMapperCustom {

    UserloginCustom findOneByName(String name) throws Exception;

    int resetPassword(Userlogin userlogin) throws Exception;
}
