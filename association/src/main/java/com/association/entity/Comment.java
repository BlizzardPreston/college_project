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
@TableName("commnt")
public class Comment implements Serializable {
//    评论id号
    private long commentID;
//    评论内容
    private String Info;
//    学号
    private long studentID;
//    赞的数量
    private int likes;
//    时间
    private Date datetime;

}
