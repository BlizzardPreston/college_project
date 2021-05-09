package com.association.controller;

import com.association.common.Result;
import com.association.common.currentDate;
import com.association.entity.Notice;
import com.association.entity.Recruit;
import com.association.service.NoticeService;
import com.association.service.StudentService;
import com.association.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private StudentService studentService;
    //时间
    currentDate date=new currentDate();

    @RequestMapping("WriteNotice")
    @ResponseBody
    public Result WriteRecruit(@RequestParam String title, @RequestParam String text, @RequestParam long studentID){
        Notice notice=new Notice(noticeService.lastNumOfNoticeID(),studentService.getClubIDByStudentID(studentID),studentService.getStudentNameById(studentID),title,text,"还没做这块",1,date.getCurrentDate());
        noticeService.WriteNotice(notice);
        return Result.success("添加recruit成功！");
    }
    @RequestMapping("WriteNoticelocal")
    @ResponseBody
    public Result WriteRecruitlocal(@RequestParam String title, @RequestParam String text){
       if (ShiroUtils.getCurrentUser().getAuthID()==1||ShiroUtils.getCurrentUser().getAuthID()==0){
           Notice notice=new Notice(noticeService.lastNumOfNoticeID(),studentService.getClubIDByStudentID(ShiroUtils.getCurrentUser().getStudentID()),studentService.getStudentNameById(ShiroUtils.getCurrentUser().getStudentID()),title,text,"还没做这块",1,date.getCurrentDate());
           noticeService.WriteNotice(notice);
           return Result.success("添加Notic成功！");
       }else{
           System.out.println("执行Notic失败..");
           return Result.fail();}
    }
    @RequestMapping("showNoticByclubID")
    @ResponseBody
    public Result showNotic(){
        int clubID=3;
//        System.out.println(noticeService.getNoticListByClubID(clubID));
        return Result.success(noticeService.getNoticListByClubID(clubID));
    }
//    本地
@RequestMapping("showNoticByclubIDlocal")
@ResponseBody
public Result showNoticlocal(){
    return Result.success(noticeService.getNoticListByClubID(ShiroUtils.getCurrentUser().getClubID()));
}
}

