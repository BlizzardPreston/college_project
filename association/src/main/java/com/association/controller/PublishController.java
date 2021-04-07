package com.association.controller;

import com.association.common.Result;
import com.association.common.currentDate;
import com.association.entity.Publish;
import com.association.entity.Recruit;
import com.association.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private PublishService publishService;
    //时间
    currentDate date=new currentDate();
    @RequestMapping("getPublishByID/{id}")
    @ResponseBody
    public Result getPublishByID(@PathVariable("id")long id){
        return Result.success(publishService.getPublishByID(id));
    }
    @RequestMapping("WritePublish")
    @ResponseBody
    public Result WritePublish(@RequestParam String title,@RequestParam String text,@RequestParam long studentID){
        Publish publish=new Publish(publishService.lastNumOfpublish(),studentID,title,text,"抹油图片",date.getCurrentDate(),1,0);
        publishService.WritePublish(publish);
        return Result.success("添加publish成功！");
    }
}
