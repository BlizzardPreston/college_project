package com.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@TableName("student")
//对应数据库student表
//可以一一对用xml的实体映射，但不好就是实体类必须只能一一对应，多加东西会报错，一般还是在xml中配置实体映射比较方便
public class  Student implements Serializable {
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
    //社团号，0为无社团
    private int clubID;
    //work 社团职位
    private String work;

}