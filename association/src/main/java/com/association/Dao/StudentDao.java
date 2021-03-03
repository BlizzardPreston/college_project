package com.association.Dao;

import com.association.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao {
Student getStudentByid(double id);
}
