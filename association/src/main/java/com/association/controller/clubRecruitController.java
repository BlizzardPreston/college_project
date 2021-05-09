package com.association.controller;

import com.association.common.Result;
import com.association.common.currentDate;
import com.association.entity.Recruit;
import com.association.service.RecruitService;
import com.association.shiro.ShiroUtils;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/clubRecruit")
public class clubRecruitController {
    @Autowired
    private RecruitService recruitService;
    //时间
    currentDate date=new currentDate();
    @RequestMapping("isWriteRecruit")
    @ResponseBody
    public Result isWriteRecruit(){
        Recruit recruit=new Recruit(recruitService.lastNumOfRecruit(),17251106123L,"aaaaa","可怕的骄傲是否趴活过来卡是否努力苦涩hi谭维维","asdada",date.getCurrentDate(),1,2);
        recruitService.isWriteRecruit(recruit);
        return Result.success();
    }
    @RequestMapping("WriteRecruit")
    @ResponseBody
    public Result WriteRecruit(@RequestParam String title,@RequestParam String text,@RequestParam long studentID){
        Recruit recruit=new Recruit(recruitService.lastNumOfRecruit(),studentID,title,text,"抹油图片",date.getCurrentDate(),1,0);
        recruitService.isWriteRecruit(recruit);
        return Result.success("添加recruit成功！");
    }
//    使用shiro获得当前用户数据
    @RequestMapping("WriteRecruitlocal")
    @ResponseBody
    public Result WriteRecruitlocal(@RequestParam String title,@RequestParam String text){
        if (ShiroUtils.getCurrentUser().getAuthID()==1||ShiroUtils.getCurrentUser().getAuthID()==0) {
            Recruit recruit = new Recruit(recruitService.lastNumOfRecruit(), ShiroUtils.getCurrentUser().getStudentID(), title, text, "抹油图片", date.getCurrentDate(), 1, 0);
            recruitService.isWriteRecruit(recruit);
            return Result.success("添加recruit成功！");
        }else {
            System.out.println("执行recruit失败..");
            return Result.fail("执行recruit失败..");}
    }
    @RequestMapping("nextNumber")
    @ResponseBody
    public Result nextRecruitID(){
        System.out.println(date.getCurrentDate());

        return Result.success(recruitService.numOfRecruit());
    }
    @RequestMapping("getRecruitByID")
    @ResponseBody
    public Result getRecruitByID(@RequestParam int rid){
        return Result.success(recruitService.getRecruitByID(rid));

    }
    @RequestMapping("deleteRecruitByRID/{id}")
    @ResponseBody
    public Result deleteRecruitByRID(@PathVariable("id") int rid){
        return Result.success(recruitService.deleteRecruitByRID(rid));

    }
    @RequestMapping("getAllRecruit")
    @ResponseBody
    public Result getAllRecruit(){
        return Result.success(recruitService.AllRecruitID());
    }
}
