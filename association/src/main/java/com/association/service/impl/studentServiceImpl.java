package com.association.service.impl;

import com.association.emtity.student;
import com.association.mapper.studentMapper2;
import com.association.service.IstudentService;
import io.swagger.annotations.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@ApiModel(value = "查询学生信息",description = "查询多个或单个学生信息")
public class studentServiceImpl implements IstudentService {
    private final studentMapper2 studentmapper2;
    public studentServiceImpl(studentMapper2 studentmapper2) {
      this.studentmapper2=studentmapper2;
    }

    @Override

    @ApiModelProperty(value = "通过ID查询学生僧信息",example = "17251106111",dataType = "double")
    public student getstudentbyid( double id) {
        return studentmapper2.getstudentbyid(id);
    }

    @Override
    @ApiModelProperty(value = "通过年级查询年级较高学生",example = "2017",dataType = "int")
    public List<student> older(int grade) {
        return studentmapper2.older(grade);
    }

    public Map<String,Double> printNameAndID(List<student> students){
        HashMap<String, Double> older = new HashMap<>();
        for (student ss : students) {
            older.put(ss.getStudentName(),ss.getStudentID());
        }
        return older;
    }
}
