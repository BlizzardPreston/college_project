package com.association.controller;

import com.association.entity.Student;
import com.association.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/student")
public class studentControllerTest {
    @Autowired
    private StudentService studentService;
    @RequestMapping("getnamebyid/{id}")
    public String getStudentNameById(@PathVariable("id") long id){
        return studentService.getStudentNameById(id);
    }
    @RequestMapping("getALLStudentlist")
    public List<Student> getAllStudent() {
        return  studentService.getAllStudent();
    }
    }


