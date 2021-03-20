package com.association.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mycomment implements Serializable {
    //评论中间表主键ID
    private long myID;
//    招聘或活动评论ID
    private long mycomID;
//    对用评论表ID
    private long commentID;
}
