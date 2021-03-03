package com.association.controller;

import com.association.emtity.student;
import com.association.service.impl.studentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ServiceTest")
@Api(value = "学生查询测试",description = "学生查询测试")
public class studentTestServiceController {
    private studentServiceImpl student;
    @Autowired
//    写Service层要注意初始化实现接口
    public void setStudent(studentServiceImpl student){this.student=student;}

    @GetMapping("/id/{id}")
    public student getid(@ApiParam(name ="学号",value = "输入学号查询",defaultValue = "17251106111",required = true) @PathVariable("id")double id){
        return student.getstudentbyid(id);
    }
    @GetMapping("oldergrade/{grade}")
    public Map<String,Double> printNameAndID(@PathVariable("grade")int grade){
        return student.printNameAndID(student.older(grade));
    }
    @GetMapping("/grade/{grade}")
    @ApiOperation(value = "查询年级比参数大的学生")
    public List showGrade(@ApiParam(name = "年级",value = "查询比年级参数大的人的名单",required =true,defaultValue = "2017") @PathVariable("grade") int grade){
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
