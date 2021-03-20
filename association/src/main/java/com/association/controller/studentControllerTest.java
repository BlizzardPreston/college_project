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
    Student s1=new Student(18251108118l,"陈添加","17金融",2018);
    @RequestMapping("addStudent")
    public List<Student> addStudentAndShow(){
        studentService.addStudent(s1);
        return getAllStudent();
    }
    @RequestMapping("delete")
    public String deleteById(){
        boolean t=studentService.deleteById(s1.getStudentID());
        if(t){return "删除成功！";}else {return "删除失败！";}
    }

}
