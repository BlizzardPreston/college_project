package com.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    //    学号
    private double studentID;
    //    用户名
    private String userName;
    //    密码
    private String userPassword;
    //    社团编号
    private int clubID;
//    用户对应角色集合
/*    该字段只能用mongodb实现
    private Set<auth> auths;*/
}
