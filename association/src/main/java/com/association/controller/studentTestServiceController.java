package com.association.controller;

import com.association.emtity.student;
import com.association.service.impl.studentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ServiceTest")

public class studentTestServiceController {
    private studentServiceImpl student;
    @Autowired
//    写Service层要注意初始化实现接口
    public void setStudent(studentServiceImpl student){this.student=student;}

    @GetMapping("/id/{id}")
    public student getid(@PathVariable("id")double id){
        return student.getstudentbyid(id);
    }
    @GetMapping("oldergrade/{grade}")
    public Map<String,Double> printNameAndID(@PathVariable("grade")int grade){
        return student.printNameAndID(student.older(grade));
    }
    @GetMapping("/grade/{grade}")
    public List showGrade(@PathVariable("grade")int grade){
        return student.older(grade);
    }
    @GetMapping("/modelGrade/{grade}")
    public ModelAndView showgrade(ModelAndView modelAndView,@PathVariable("grade")String grade){
        Map<String, String> stringMap=new HashMap<>();
        stringMap.put("1",grade);
        modelAndView.addAllObjects(stringMap);
    return modelAndView;
    }
}
