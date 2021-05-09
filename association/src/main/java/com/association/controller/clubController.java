package com.association.controller;

import com.association.Dao.StudentDao;
import com.association.common.Result;
import com.association.entity.Club;
import com.association.service.ClubService;
import com.association.service.StudentService;
import com.association.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            int cid=studentService.getStudentById(id).getClubID();
            studentService.deleteClubByID(id);
            clubService.updataClubNum(cid,studentService.getStudentListByClubID(cid).size());
            System.out.println("执行成功，clubID置0");
            return Result.success("成功删除！");
    }
    @RequestMapping("addClubMenber1")
    @ResponseBody
    public Result addClubMenber1(HttpServletRequest request){
        System.out.println(request.getParameter("studentID"));
        long id = Long.parseLong(request.getParameter("studentID"));
        String name=request.getParameter("studentName");
        String work=request.getParameter("work");
        int cid=Integer.parseInt(request.getParameter("clubID"));
        studentService.addClubMenber(id,name,work,cid);

        return Result.success("添加用户成功！");
    }
    @RequestMapping(value = "/addClubMenber2",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result addClubMenber2(@RequestParam long studentID,@RequestParam(value = "studentName",required = false) String name ,@RequestParam(value = "work",required = false) String work,@RequestParam int authID,@RequestParam(value = "clubID",required = false) int cid) {
        if(ShiroUtils.getCurrentUser().getAuthID()==3){
            System.out.println("用户权限不足");
            return Result.fail();
        }
        System.out.println("addclubID:"+studentID+name+work+cid);
        studentService.addClubMenber(studentID, name, work, cid);
        System.out.println(clubService.updataClubNum(cid, studentService.getStudentListByClubID(cid).size()));
        clubService.updataClubNum(cid,studentService.getStudentListByClubID(cid).size());
        if(studentService.updateUserClubID(studentID,cid,authID)){
            return Result.success("添加用户成功！");
        }else return Result.success("student添加成功，但此人无平台账号");


    }
    @RequestMapping("AllClubList")
    @ResponseBody
    public Result AllClubList(){
       return Result.success(clubService.AllClubList());
    }
    @RequestMapping("getClubNameByID")
    @ResponseBody
    public Result getClubNameByID(@RequestParam int clubID){
        return Result.success(clubService.getClubByID(clubID));
    }
//通过shiro获得用户信息
    @RequestMapping(value = "/getStudentListByShiro",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result getStudentListByShiro(){
        System.out.println("Shiro studentlist--->>"+ ShiroUtils.getCurrentUser().getClubID());
        return Result.success(clubService.getStudentListByClubID(ShiroUtils.getCurrentUser().getClubID()));
    }
    @RequestMapping(value = "/getClubByShiro",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Result getClubByShiro(){
        System.out.println("Shiro clubID--->>"+ShiroUtils.getCurrentUser().getClubID());
        return Result.success(clubService.getClubByID(ShiroUtils.getCurrentUser().getClubID()));
    }


    //随机生成测试
    Random random = new Random();
//    int id= random.nextInt(4);
    int id=3;
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
