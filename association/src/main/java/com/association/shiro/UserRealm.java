package com.association.shiro;

import com.association.config.shiro.RedisSessionDao;
import com.association.entity.User;
import com.association.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.util.Collection;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    @Qualifier("redisSessionDao")
    @Lazy
    private RedisSessionDao redisSessionDao;
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
        SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(userService.getUserById(Long.valueOf(usertoken.getPrincipal().toString())),password,ByteSource.Util.bytes(userName), "UserRealm");
        stopPreviousSession(user.getStudentID());
        return info;
    }

    /**
     * 踢掉上一个登录的同名用户
     *
     * @param id 主键
     */

    private void stopPreviousSession(Long id) {
        Collection<Session> sessions = redisSessionDao.getActiveSessions();
        Session currSession = ShiroUtils.getSession();
        Serializable sId = currSession.getId();
        for (Session session : sessions) {
            SimplePrincipalCollection collection = (SimplePrincipalCollection) session
                    .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (collection == null) {
                continue;
            }

            User u = (User) collection.getPrimaryPrincipal();
            if (id.equals(u.getStudentID())) {
                if (sId.equals(session.getId())) {
                    continue;
                }
                redisSessionDao.delete(session);
            }
        }
    }
}
