package com.association.service.impl;

import com.association.emtity.user;
import com.association.mapper.LoginMapper;
import com.association.service.ILoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class ILoginServiceImpl implements ILoginService {
//    初始化
    private final LoginMapper loginmapper;
    public ILoginServiceImpl(LoginMapper loginmapper) {
        this.loginmapper=loginmapper;
    }
//    设置登录授权验证方法
    public Subject shiroLoginByNameAndPassw(String id,String pw){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(id,pw);
        subject.login(usernamePasswordToken);


        return subject;
    }
    //通过shiro获取用户信息
    public user getUserByShiro(){
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return (user) SecurityUtils.getSubject().getPrincipal();
    }
    //通过直接查询获取用户信息


    @Override
    public user getuserbylogin(double id, String pw) {
        return loginmapper.getuserbylogin(id,pw);
    }

    @Override
    public user getuserbyid(double id) {
        return loginmapper.getuserbyid(id);
    }

    @Override
    public user getMapByID(double id) {
        return loginmapper.getMapByID(id);
    }

    @Override
    public String chackUserAuthInfo(double id) {
        return loginmapper.chackUserAuthInfo(id);
    }

    @Override
    public user getUserByName(String name) {
        return loginmapper.getUserByName(name);
    }
}
