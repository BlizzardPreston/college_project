package com.association.service;

import com.association.Dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;

    public String getStudentNameById(double id){
        return studentDao.getStudentByid(id).getStudentName();
    }

}
