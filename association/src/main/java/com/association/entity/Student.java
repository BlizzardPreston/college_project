package com.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student")
//对应数据库student表
public class Student implements Serializable {
    //    学号
    private long studentID;
    //    学生名
    private String studentName;
    //    学生专业
    private String studentMajor;
    //    学生年级
    private int studentGrade;
    //    学生性别
    private String studentGender;

}