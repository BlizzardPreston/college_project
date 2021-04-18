package com.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice")
public class Notice implements Serializable {
//    社团公告id号
    private long noticeID;
//    社团id
    private int clubID;
//    发送人姓名
    private String sendname;
//    标题
    private String title;
//    公告内容
    private String Info;
//    图片路径
    private String imgURL;
//    评论ID号
    private long mycomID;
//    发布时间
    private LocalDateTime datetime;
}
