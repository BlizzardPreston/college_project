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
@TableName("email")
public class Email implements Serializable {
    //    邮件id
    private long emailID;
    //    发送人id
    private long studentIDfrom;
    //    接收人id
    private long studentIDto;
    //    标题
    private String title;
    //    文本信息
    private String text;
    //    图片路径
    private String imgURL;
    //    时间
    private Date datetime;
}
