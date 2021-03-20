package com.association.controller;

import com.association.common.Result;
import com.association.entity.User;
import com.association.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class logincontrollertest {
/*    private ILoginService login;
    @Autowired
    public void setlogin(ILoginService login){
        this.login=login;
    }*/
    @Autowired
    private UserService login;

    @GetMapping("testmapperlogin1/{id}/{pw}")
    public User getuserbylogin(@PathVariable("id") long id, @PathVariable("pw") String pw){
        return login.getuserbylogin(id,pw);
}
    @GetMapping("testmapperlogin2/{id}")
    public User getuserbyid(@PathVariable("id") long id){
        return login.getUserById(id);
    }
    @GetMapping("chackUserINFO/{id}")
    public String chackUserAuthInfo(@PathVariable("id")long id){
        return login.chackUserAuthInfo(id);
    }
    @RequestMapping("testResult/{id}")
    public Result getuserbyid2(@PathVariable("id") long id){
        return Result.success(login.getUserById(id));
    }
}
