package com.association.emtity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class user implements Serializable {
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
