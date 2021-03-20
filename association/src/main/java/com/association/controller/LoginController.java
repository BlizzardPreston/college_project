package com.association.controller;
import com.association.service.UserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.association.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class LoginController {
/*    private ILoginServiceImpl login;
    @Autowired
    public void setlogin(ILoginServiceImpl login){
        this.login=login;
    }*/
    @Autowired
    private UserService login;
//    返回首页
    @RequestMapping(value = "toLogin",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(){
        return "Login";
    }

//    注意对应form表单中的action
    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(HttpServletRequest request, HttpSession session, HashMap<String, Object> map, Model model)
    {
        long id = Long.parseLong(request.getParameter("id"));
        String pw = request.getParameter("pw");
//        request.getCookies().
        User user = login.getuserbylogin(id,pw);
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
        long id = Long.parseLong(request.getParameter("id"));
        String pw = request.getParameter("pw");
        User user = login.getuserbylogin(id,pw);
        try{
            login.shiroLoginByNameAndPassw(request.getParameter("id"),pw);
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "shiroLogin";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "shiroLogin";
        }
    }
}
