package com.association.controller;

import com.association.common.Result;
import com.association.entity.Student;
import com.association.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/student")
public class studentControllerTest {
    @Autowired
    private StudentService studentService;
    @RequestMapping("getnamebyid/{id}")
    @ResponseBody
    public String getStudentNameById(@PathVariable("id") long id){
        return studentService.getStudentNameById(id);
    }
    @RequestMapping("getALLStudentlist")
    @ResponseBody
    public List<Student> getAllStudent() {
        return  studentService.getAllStudent();
    }

    @RequestMapping("register")
    @ResponseBody
    public Result doRegister(){

        return Result.success();
    }

}


