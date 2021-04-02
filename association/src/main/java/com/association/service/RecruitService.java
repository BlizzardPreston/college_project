package com.association.service;

import com.association.Dao.RecruitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitService {
    @Autowired
    private RecruitDao recruitDao;

    public boolean isWriteRecruit(long id,String title,String text){
        return recruitDao.isWriteRecruit(id,title,text);
    };
    public int numOfRecruit(){
        return recruitDao.AllRecruitID().size();
    }
}
