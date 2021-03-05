package com.association.Dao;


import com.association.entity.Student;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    Student getStudentByid(@Param(("studentID")) double id);
    //    查询所有学生信息
    List<Student> getAllStudent();

    Student getStudentById(double id);

    Student getStudentByName(String name);

    Student getStudentByGrade(int grade);

    String getsStudentNameById(double id);

    boolean is2017(@Param("studentID") double id);
}
