package com.association.controller;


import com.association.common.Result;
import com.association.entity.User;
import com.association.service.UserService;
import com.association.shiro.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RestController
@RequestMapping(value = "/Index")
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/getUsername",method = RequestMethod.GET)
    public String getUserName(){
        User user = ShiroUtils.getCurrentUser();
        return userService.getUserById(user.getStudentID()).getUserName();
    }

    @RequestMapping(value = "/getUsername2",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result getUserName2(@RequestParam String id,@RequestParam String name){

//        return userService.getUserById(Double.valueOf(id)).getUserName();
        System.out.println(".............执行getUserName2........");
        System.out.println(name);
        return Result.success(userService.getUserById(Long.valueOf(id)));
    }
    @RequestMapping(value = "/getAuthName",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getAuthName(){
//        long id=17251106111l;
        System.out.println("执行了---->>IndexController类的 <getAuthID> 方法");
        System.out.println("返回userName为： "+ShiroUtils.getCurrentUser().getUserName());
        return ShiroUtils.getCurrentUser().getUserName();
    }


}
