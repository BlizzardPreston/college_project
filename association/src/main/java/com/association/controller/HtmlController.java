package com.association.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/html")
public class HtmlController {
    @RequestMapping("news")
    public String toNewsHtml(){
        return "news";
    }
    @RequestMapping("manage")
    public String toManageHtml(){
        return "manage";
    }
    @RequestMapping("register")
    public String toRegisterHtml(){
        return "register";
    }
    @RequestMapping("edit")
    public String toEditHtml(){
        return "edit";
    }
    @RequestMapping("Recruitment")
    public String toRecruitmentHtml(){
        return "Recruitment";
    }
    @RequestMapping("club_recruit")
    public String toClub_recruitHtml(){
        return "Recruitment";
    }
    @RequestMapping("index_v2")
    public String toindex_v2Html(){
        return "index_v2";
    }
    @RequestMapping("publish")
    public String toPublishHtml(){
        return "publish";
    }
    @RequestMapping("shiroLogin")
    public String toShiroLoginHtml(){
        return "ShiroLogin";
    }

}

