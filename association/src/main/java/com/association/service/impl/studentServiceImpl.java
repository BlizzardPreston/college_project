package com.association.service.impl;

import com.association.emtity.student;
import com.association.mapper.studentMapper2;
import com.association.service.IstudentService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class studentServiceImpl implements IstudentService {
    private final studentMapper2 studentmapper2;
    public studentServiceImpl(studentMapper2 studentmapper2) {
      this.studentmapper2=studentmapper2;
    }

    @Override
    public student getstudentbyid(double id) {
        return studentmapper2.getstudentbyid(id);
    }

    @Override
    public List<student> older(int grade) {
        return studentmapper2.older(grade);
    }

    public Map<String,Double> printNameAndID(List<student> students){
        HashMap<String, Double> older = new HashMap<>();
        for (student ss : students) {
            older.put(ss.getStudentName(),ss.getStudentID());
        }
        return older;
    }
}
