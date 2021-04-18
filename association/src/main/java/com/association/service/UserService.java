package com.association.service;

import com.alibaba.druid.sql.PagerUtils;
import com.association.Dao.UserDao;
import com.association.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
//    shiro
    public Subject shiroLoginByNameAndPassw(String id, String pw){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(id,pw);
        subject.login(usernamePasswordToken);
        return subject;
    }
    //通过shiro获取用户信息
    public User getUserByShiro(){
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
    //通过直接查询获取用户信息
    public User getUserById(long id){
        return userDao.getUserById(id);
    }
    public User getUserByName(String name){
        return userDao.getUserByName(name);
    }
    public User getuserbylogin(long id,String pw){
        return userDao.getUserByLogin(id,pw);
    }
    public String chackUserAuthInfo(long id){
        return userDao.chackUserAuthInfo(id);
    }
    public boolean addUser(User user) {
        if (userDao.addUser(user)) {
            System.out.println("添加user成功!");
            return true;
        }else {
            System.out.println("添加user失败！");
            return false;
        }

        }
}
