package com.association.controller;

import com.association.emtity.club;
import com.association.emtity.student;
import com.association.mapper.clubmapper;
import com.association.mapper.studentmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/studenttest")
public class studentTestController {
    @Autowired
    studentmapper student;

    @Autowired
    clubmapper cl;
    @GetMapping("getstudent")
    public List<student> getallstudent(){
        return student.getallstudent();
    }
    @GetMapping("/getstudentbyid/{id}")
    public student getstudentbyid(@PathVariable("id") double id){
        return student.getstudentbyid(id);
    }
    @GetMapping("getstudentbyname/{name}")
    public student getstudentbyname(@PathVariable("name") String name){
        return student.getstudentbyname(name);
    }
    @GetMapping("getstudentbygrade/{grade}")
    public student getstudentbygrade(@PathVariable("grade") int grade){
        return student.getstudentbygrade(grade);
    }

    @GetMapping("/getstudentnamebyid/{id}")
    public String getstudentnamebyid(@PathVariable("id") double id){
        return student.getstudentnamebyid(id);
    }
    @GetMapping("/getclubbyid/{id}")
    public club getclubbyid(@PathVariable("id") int id) {
        return cl.getclubbyid(id);}

    @GetMapping("is2017/{id}")
    public String iss2017(@PathVariable("id") double id){
        if (student.is2017(id)){
            return "是";
        }else return "否";
    }
}
