package com.association.Dao;


import com.association.entity.Student;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    Student getStudentByid(@Param("studentID") long id);
    //    查询所有学生信息
    List<Student> getAllStudent();

    Student getStudentById(long id);

    Student getStudentByName(String name);

    Student getStudentByGrade(int grade);

    String getsStudentNameById(long id);

    boolean is2017(@Param("studentID") long id);
    int addStudent(Student student);
    boolean deleteById(@Param("studentID")long id);
}
