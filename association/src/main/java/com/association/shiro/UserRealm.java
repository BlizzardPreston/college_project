package com.association.shiro;

import com.association.entity.User;
import com.association.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
/*    @Autowired
    private ILoginServiceImpl login;*/
    @Autowired
    private UserService userService;
//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
//认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("--------------执行认证工作----------------");
        Subject subject = SecurityUtils.getSubject();
//        封装用户的登数据
/*       写死测试用
        String id="17251106111";
        String pw="123456";
        */
        UsernamePasswordToken usertoken = (UsernamePasswordToken)authenticationToken;
        User user = userService.getUserById(Long.valueOf(usertoken.getUsername()));
        String userName=usertoken.getPrincipal().toString();
        System.out.println("打印userName："+userName);

        if (user==null){
            System.out.println("注意！！---->>>>>>> [Userrealm] 中user为空");
            return null;
//            抛出UnknowAccountException异常
        }
        //获取数据库中的密码
        String password=user.getUserPassword();
        System.out.println("打印password: "+password);

        System.out.println("**************认证成功*******************USERID：<"+usertoken.getUsername()+">");
        //返回值 1、身份标识(决定当前用户的返回类型，所以我想办法变成userl类【踩坑】) 2、用户密码 3、用userName作为盐值（加密用）  4、Realm文件名
        return new SimpleAuthenticationInfo(userService.getUserById(Long.valueOf(usertoken.getPrincipal().toString())),password,ByteSource.Util.bytes(userName), "UserRealm");

    }
}
