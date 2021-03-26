package com.association.service;

import com.association.Dao.StudentDao;
import com.association.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public String getStudentNameById(long id){
        System.out.println("StudentService------->>>getStudentName");
        return studentDao.getStudentByid(id).getStudentName();
    }
    public List<Student> getAllStudent(){
        return studentDao.getAllStudent();
    }
    public int addStudent(Student student){
    if (studentDao.addStudent(student)==1){
        System.out.println("添加student成功!");
        return 1;
    }else {
        System.out.println("添加失败！");
        return 0;
    }
    };
    public boolean deleteById(long id){
        if(studentDao.deleteById(id)){
            System.out.println("成功删除此学生: "+id);
            return true;
        }else return false;
    }
//    把学生从社团删除，相当于把学生表的clubID改为0
    public boolean deleteClubByID(long id){
        return studentDao.updataStudentClubID(id,studentDao.getsStudentNameById(id),0);
    }
    public boolean addClubMenber(long id,String name ,String work,int cid){
        return studentDao.addClubMenber(id,name,work,cid);
    }

}
