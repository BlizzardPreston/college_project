package com.association.emtity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.Permissions;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth")
//权限表
public class auth implements Serializable {
//    权限ID，1为root，2为admin，3为一般
    private int authID;
//    权限名
    private String authName;
//    权限信息
    private String authInfo;
    /**
     * 角色对应权限集合
     */
/*    该字段只能用mongodb实现
    private Set<Permission> permissions;*/
}
