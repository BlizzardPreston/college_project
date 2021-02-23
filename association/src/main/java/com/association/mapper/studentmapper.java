package com.association.mapper;

import com.association.emtity.student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface studentmapper {
//    查询所有学生信息
    List<student> getallstudent();

    student getstudentbyid(double id);

    student getstudentbyname(String name);

    student getstudentbygrade(int grade);

    String getstudentnamebyid(double id);

    boolean is2017(@Param("studentID") double id);

}
