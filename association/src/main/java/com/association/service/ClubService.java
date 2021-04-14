package com.association.service;

import com.association.Dao.ClubDao;
import com.association.Dao.StudentDao;
import com.association.entity.Club;
import com.association.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class ClubService {
    @Autowired
    private ClubDao clubDao;
    @Autowired
    private StudentDao studentDao;

    public Club getClubByID(int id){
        return clubDao.getClubById(id);
    }
    public String getClubNamebyID(int id){
        return clubDao.getClubNameById(id);
    }
    public List<Student> getStudentListByClubID(int id){return studentDao.getStudentListByClubID(id);}
    public boolean updataClubNum(int cid,int num){return clubDao.updataClubNum(cid,num);}
    public List<Club> AllClubList(){
        return clubDao.AllClubList();
    }
}
