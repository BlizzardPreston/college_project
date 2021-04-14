package com.association.Dao;

import com.association.entity.Club;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubDao {
    Club getClubById(@Param("clubID")int id);
    String getClubNameById(@Param("clubID")int id);
    int getClubNumById(@Param("clubID")int id);
    boolean updataClubNum(@Param("clubID")int cid,@Param("clubNum")int num);
    List<Club> AllClubList();
}
