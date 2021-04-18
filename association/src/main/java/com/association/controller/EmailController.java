package com.association.controller;

import com.association.common.Result;
import com.association.common.currentDate;
import com.association.entity.Email;
import com.association.service.EmailService;
import com.association.shiro.ShiroUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    //时间
    currentDate date=new currentDate();
    @RequestMapping("WriteEmail")
    @ResponseBody
    public Result WriteEmail(@RequestParam long studentIDfrom,@RequestParam String title,@RequestParam String text,@RequestParam long studentIDto){
        Email email=new Email(emailService.lastNumOfEmailID(),studentIDfrom,studentIDto,title,text,"",date.getCurrentDate());
        return Result.success(emailService.WriteEmail(email));
    }
    @RequestMapping("showEmailByStudentID")
    @ResponseBody
    public Result showNotic(){
        long id=17251106111L;
//        System.out.println(emailService.getEmailListByStudentIDto(id));
        return Result.success(emailService.getEmailListByStudentIDto(id));
    }
//    本地
    @RequestMapping("WriteEmaillocal")
    @ResponseBody
    public Result WriteEmaillocal(@RequestParam long studentIDto,@RequestParam String title,@RequestParam String text){
        Email email=new Email(emailService.lastNumOfEmailID(), ShiroUtils.getCurrentUser().getStudentID(),studentIDto,title,text,"",date.getCurrentDate());
        return Result.success(emailService.WriteEmail(email));
    }
    @RequestMapping("showEmailByStudentIDlocal")
    @ResponseBody
    public Result showNoticlocal(){

        return Result.success(emailService.getEmailListByStudentIDto(ShiroUtils.getCurrentUser().getStudentID()));
    }
}

