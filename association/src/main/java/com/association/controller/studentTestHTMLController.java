package com.association.controller;

import com.association.mapper.studentmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/html")

public class studentTestHTMLController {

    @Autowired
    studentmapper student;

    @RequestMapping("/getstudenttest")
    public String getstudenttest(Model model) {
        model.addAttribute("msg", student.getallstudent());
        return "getstudenttest";
    }
    @RequestMapping("/login2")
    public String getlogin2(){
        return "Login";
    }
    @RequestMapping("/login")
    public String getlogin(){
        return "Login";
    }

}
