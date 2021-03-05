package com.association.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permission {
    //    权限对应实体类
    private String permissionID;
    private String permissionsName;
}
