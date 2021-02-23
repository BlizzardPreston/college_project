package com.association.controller;

import com.association.emtity.user;
import com.association.mapper.LoginMapper;
import com.association.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class logincontrollertest {
    private ILoginService login;
    @Autowired
    public void setlogin(ILoginService login){
        this.login=login;
    }

    @GetMapping("testmapperlogin1/{id}/{pw}")
    public user getuserbylogin(@PathVariable("id") double id, @PathVariable("pw") String pw){
        return login.getuserbylogin(id,pw);
}
    @GetMapping("testmapperlogin2/{id}")
    public user getuserbyid(@PathVariable("id") double id){
        return login.getuserbyid(id);
    }
    @GetMapping("chackUserINFO/{id}")
    public String chackUserAuthInfo(@PathVariable("id")double id){
        return login.chackUserAuthInfo(id);
    }
}
