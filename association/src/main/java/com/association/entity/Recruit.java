package com.association.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("recruit")
public class Recruit implements Serializable {
//    招聘ID号
    private int recruitID;
//    学生号
    private long studentID;
    //标题
    private String title;
//    招聘文本
    private String text;
//    图片路径
    private String imgURL;
//    发布时间
    private Date datetime;
//    是否已发送
    private int isSend;
//    评论对应id
    private long mycomID;
}
