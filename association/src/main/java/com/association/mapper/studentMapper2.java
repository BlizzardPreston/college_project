package com.association.mapper;

import com.association.emtity.student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface studentMapper2 {
    student getstudentbyid(@Param("studentID") double id);
    List<student> older(@Param("studentGrade") int grade);
}
