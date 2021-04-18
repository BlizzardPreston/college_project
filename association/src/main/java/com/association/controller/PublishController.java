package com.association.controller;

import com.association.common.Result;
import com.association.common.currentDate;
import com.association.entity.Publish;
import com.association.entity.Recruit;
import com.association.service.PublishService;
import com.association.service.StudentService;
import com.association.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    private PublishService publishService;
    @Autowired
    private StudentService studentService;
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
    @RequestMapping("WritePublishlocal")
    @ResponseBody
    public Result WritePublish(@RequestParam String title,@RequestParam String text){
        Publish publish=new Publish(publishService.lastNumOfpublish(), ShiroUtils.getCurrentUser().getStudentID(),title,text,"抹油图片",date.getCurrentDate(),1,0);
        publishService.WritePublish(publish);
        return Result.success("添加publish成功！");
    }
    @RequestMapping("getAllPublishByid")
    @ResponseBody
//    public Result getAllPublishByid(@RequestParam long studentID){
    public Result getAllPublishByid(){
        long studentID=17251106111L;
        return Result.success(publishService.AllPublishByStudentID(studentID));
    }
//    前端接口
    @RequestMapping("getStudent")
    @ResponseBody
    public Result getStudent(){
        long id=17251106111L;
//        System.out.println(studentService.getStudentById(id));
        return Result.success(studentService.getStudentById(id));
    }

    @RequestMapping("AllPublish")
    @ResponseBody
    public Result getAllPublish(){
        return Result.success(publishService.AllPublish());
    }
    @RequestMapping("getStudentByID")
    @ResponseBody
    public Result getStudentByID(@RequestParam long id){
        return Result.success(studentService.getStudentById(id));
    }
    @RequestMapping("deleteByPublishId")
    @ResponseBody
    public Result deleteByPublishId(@RequestParam int publishID){return Result.success(publishService.deleteByPublishId(publishID));}
//    本地接口
    @RequestMapping("getStudentlocal")
    @ResponseBody
    public Result getStudentlocal(){

    return Result.success(studentService.getStudentById(ShiroUtils.getCurrentUser().getStudentID()));
}
    @RequestMapping("getAllPublishByidlocal")
    @ResponseBody
    public Result getAllPublishByidlocal(){
        return Result.success(publishService.AllPublishByStudentID(ShiroUtils.getCurrentUser().getStudentID()));
    }
}
