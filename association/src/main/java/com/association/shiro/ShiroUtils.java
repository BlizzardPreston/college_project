package com.association.shiro;

import com.association.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
    public static Session getSession() {
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static User getCurrentUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal==null){
            System.out.println("----->>>>err：pringipal为空！！");
            return null;
        }else {
            System.out.println("ShiroUtils类中->getCurrentUser->> principal； "+principal);
        }
        if (principal instanceof User) {
            System.out.println("成功返回principal！");

            return (User) principal;
        }
//        throw ShiroException(){}
        System.out.println("getCurrentUser---err");
        return null;
    }
}
