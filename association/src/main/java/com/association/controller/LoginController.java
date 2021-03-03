package com.association.controller;
import com.association.service.ILoginService;
import com.association.service.IstudentService;
import com.association.service.impl.ILoginServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.ui.Model;
import com.association.emtity.user;
import com.association.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("user")
@Api(value = "LoginController|适用于登录界面与登录跳转",description = "登录界面与登录跳转")
public class LoginController {
    private ILoginServiceImpl login;
    @Autowired
    public void setlogin(ILoginServiceImpl login){
        this.login=login;
    }

    /*private studentServiceImpl student;
    @Autowired
    public void setStudent(studentServiceImpl student){this.student=student;}*/
//    返回首页
    @RequestMapping(value = "toLogin",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(){
        return "Login";
    }

//    注意对应form表单中的action
    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "简单查询实现登录")
    public String login(HttpServletRequest request, HttpSession session, HashMap<String, Object> map, Model model)
    {
        double id = Double.parseDouble(request.getParameter("id"));
        String pw = request.getParameter("pw");
        user user = login.getuserbylogin(id,pw);
//        查不到则返回null
        session.setAttribute("user", user);
        if (user == null) {
            model.addAttribute("msg", "请输入正确的账号和密码");
            return "Login";
        } else
            return "Index";
    }

    @RequestMapping(value = "toShiroLogin",method = {RequestMethod.POST,RequestMethod.GET})
    public String shirologin(){
        return "shiroLogin";
    }
    @RequestMapping(value = "/shiroLogin",method = {RequestMethod.POST,RequestMethod.GET})
    public String shiroLogin(HttpServletRequest request, HttpSession session, HashMap<String, Object> map, Model model)
    {
        double id = Double.parseDouble(request.getParameter("id"));
        String pw = request.getParameter("pw");
        user user = login.getuserbylogin(id,pw);
        try{
            login.shiroLoginByNameAndPassw(request.getParameter("id"),pw);
            return "redirect:UserIndex/"+id;
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "shiroLogin";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "shiroLogin";
        }
    }
    @RequestMapping(value = "UserIndex/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    public String getUserName(@PathVariable("id") double id,HttpServletRequest request , HttpSession session, HttpServletResponse response, Model model){
        System.out.println("***************  success!!  ****************");
        model.addAttribute("username",login.getuserbyid(id).getUserName());
        model.addAttribute("msg","成功获取");
        return "Index";
    }
    @RequestMapping(value = "UserData",method = {RequestMethod.GET,RequestMethod.POST})
    public user getUserData(){
        return new user();
    }
}
