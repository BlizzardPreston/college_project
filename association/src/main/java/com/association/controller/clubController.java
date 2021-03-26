package com.association.controller;

import com.association.Dao.StudentDao;
import com.association.common.Result;
import com.association.entity.Club;
import com.association.service.ClubService;
import com.association.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping("/club")
public class clubController {
    @Autowired
    private ClubService clubService;
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "/getClubByID/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Club getClubByID(@PathVariable("id") int id){
        return clubService.getClubByID(id);
    }
    @RequestMapping(value = "/getNameByID/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String getNameByID(@PathVariable("id") int id){
        return clubService.getClubNamebyID(id);
    }
    @RequestMapping(value = "/getStudentListByClubID/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result getStudentListByClubID(@PathVariable("id") int id){
        return Result.success(clubService.getStudentListByClubID(id));
    }
    @RequestMapping(value = "/getStudentListByID/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result getStudentListByID(@PathVariable("id") int id){
        return Result.success(id);
    }

    @RequestMapping(value = "/addClubIfExit",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result addClubIfExit(@RequestParam long id){
        return Result.success();
    }

    @RequestMapping(value = "deleteById",method = {RequestMethod.POST})
    @ResponseBody
    public Result deleteById(@RequestParam("id") long id){
        System.out.println(id);
        if(studentService.deleteClubByID(id)) {
            System.out.println("执行成功，clubID置0");
            return Result.success("成功拉！");
        }else {
            System.out.println("执行deleteById失败，请查询数据库确认结果");
            return Result.success("失败");
        }
    }
    @RequestMapping("addClubMenber")
    @ResponseBody
    public Result addClubMenber(@RequestParam("studentID") long id,@RequestParam("studentName")String name,@RequestParam("work") String work){
        return Result.success();
    }






    //随机生成测试
    Random random = new Random();
    int id= random.nextInt(4);
    @RequestMapping(value = "/getStudentListByRandom",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result getStudentListByRandom(){

        System.out.println("ran studentlist--->>"+id);
        return Result.success(clubService.getStudentListByClubID(id));
    }
    @RequestMapping(value = "/getClubByRandom",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result getClubByRandom(){

        System.out.println("ran clubID--->>"+id);
        return Result.success(clubService.getClubByID(id));
    }

}
