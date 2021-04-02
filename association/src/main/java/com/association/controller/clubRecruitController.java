package com.association.controller;

import com.association.common.Result;
import com.association.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;


@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/clubRecruit")
public class clubRecruitController {
    @Autowired
    private RecruitService recruitService;

    @RequestMapping("isWriteRecruit")
    @ResponseBody
    public Result isWriteRecruit(){
        recruitService.isWriteRecruit(2,"ceshi","ceshi ceshi");
        return Result.success();
    }
    @RequestMapping("nextNumber")
    @ResponseBody
    public Result nextRecruitID(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String validityDate2 = simpleDateFormat.format(date);
        java.sql.Date result=java.sql.Date.valueOf(validityDate2);
        System.out.println(date);
        System.out.println(date.getClass());
        System.out.println(validityDate2);
        System.out.println(validityDate2.getClass());
        System.out.println(result);
        System.out.println(result.getClass());
        return Result.success(recruitService.numOfRecruit()+1);
    }
}
