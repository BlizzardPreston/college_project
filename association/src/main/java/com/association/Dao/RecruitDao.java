package com.association.Dao;

import com.association.entity.Recruit;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitDao {
    boolean isWriteRecruit(@Param("studentID")long studentID,@Param("title")String title,@Param("text")String text);
    List<Recruit> AllRecruitID();



}
