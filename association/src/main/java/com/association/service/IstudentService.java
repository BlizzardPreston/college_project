package com.association.service;

import com.association.emtity.student;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IstudentService {
    student getstudentbyid( double id);
    List<student> older(int grade);
}
