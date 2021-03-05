package com.association.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("club")
//社团表
public class Club  implements Serializable {
    //    社团编号
    private int clubID;
    //    社团名
    private String clubName;
    //    社团人数
    private int clubNum;
    //    社团信息
    private String clubInfo;
    //    社团管理人
    private String clubAdmin;
}