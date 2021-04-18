package com.association.controller;
import com.association.common.Result;
import com.association.entity.Student;
import com.association.service.StudentService;
import com.association.service.UserService;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.association.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
@CrossOrigin(origins = "*",maxAge = 3600)
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
    @Autowired
    private StudentService studentService;

//    返回首页
    @RequestMapping(value = "toLogin",method = {RequestMethod.POST,RequestMethod.GET,RequestMethod.OPTIONS,RequestMethod.DELETE})
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
    @RequestMapping("register")
    @ResponseBody
    public Result  register(@RequestParam long studentID,@RequestParam String password,@RequestParam String userName,@RequestParam String studentName,@RequestParam int integer,@RequestParam String Major,@RequestParam String Gender){
        User user=new User(studentID,userName,password,0,3);
        Student student=new Student(studentID,studentName,Major,integer,Gender,0,"");
//        login.addUser(user);
//        studentService.addStudent(student);
        if(login.addUser(user)&& studentService.addStudent(student)){
            return Result.success("success to add user and student!");
        }else return Result.fail();

    }
    @RequestMapping(value = "/shiroLoginHtml",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result shiroLoginhtml(@RequestParam long id,@RequestParam String pw)
    {
        User user = login.getuserbylogin(id,pw);
        try{
            login.shiroLoginByNameAndPassw(Long.toString(id),pw);
            return Result.success();
        }catch (UnknownAccountException e){
            System.out.println("用户名错误");
            return Result.fail();
        }catch (IncorrectCredentialsException e){
            System.out.println("密码错误");
            return Result.fail();
        }
    }
}
