package com.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("activity")
public class Activity implements Serializable {
    private int activityID;
    private int clubID;
    private String title;
    private String text;
    private String imgURL;
    private String address;
    private String activityTime;
    private LocalDateTime datetime;
    private int state;
}
