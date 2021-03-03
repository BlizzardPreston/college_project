package com.association.shiro;

import com.association.emtity.user;
import com.association.service.impl.ILoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private ILoginServiceImpl login;
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

        user user = login.getuserbyid(Double.valueOf(usertoken.getUsername()));

        if (user==null){
            return null;
//            抛出UnknowAccountException异常
        }
        System.out.println("**************认证成功*******************USER："+usertoken.getUsername());
        return new SimpleAuthenticationInfo("",user.getUserPassword(),"");

    }
}
