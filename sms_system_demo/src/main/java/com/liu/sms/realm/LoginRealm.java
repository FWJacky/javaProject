package com.liu.sms.realm;

import com.liu.sms.po.Role;
import com.liu.sms.po.Userlogin;
import com.liu.sms.service.RoleService;
import com.liu.sms.service.UserloginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName LoginRealm
 * @Description TODO
 * @Author L
 * @Date 2019/8/31 17:31
 * @Version 1.0
 **/

@Component
public class LoginRealm extends AuthorizingRealm {

    @Autowired
    private UserloginService userloginService;

    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) getAvailablePrincipal(principalCollection);

        Role role = null;

        try {
            Userlogin userlogin = userloginService.findByName(username);
            //获取角色对象
            role = roleService.findByid(userlogin.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //通过用户名从数据库获取权限/角色信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> r = new HashSet<String>();
        if (role != null) {
            r.add(role.getRolename());
            info.setRoles(r);
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //用户名
        String username = (String) authenticationToken.getPrincipal();
        //密码
        String password = new String((char[])authenticationToken.getCredentials());

        Userlogin userlogin = null;
        try {
            userlogin = userloginService.findByName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //md5加盐加密
        String dataBasePassword = userlogin.getPassword();
        Object salt = ByteSource.Util.bytes(username);
        String passwordEncoded = new SimpleHash("md5",dataBasePassword,salt,1024).toString();
        if (userlogin == null) {
            //没有该用户名
            throw new UnknownAccountException();
        } else if (!password.equals(dataBasePassword)) {
            //密码错误
            throw new IncorrectCredentialsException();
        }

        //身份验证通过,返回一个身份信息,该身份的密码通过了md5加盐加密
        AuthenticationInfo aInfo = new SimpleAuthenticationInfo(username,passwordEncoded,ByteSource.Util.bytes(username),getName());
        return aInfo;
    }
}
