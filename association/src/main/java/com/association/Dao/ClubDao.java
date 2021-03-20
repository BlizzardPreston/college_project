package com.association.Dao;

import com.association.entity.Club;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubDao {
    Club getClubById(@Param("clubID")int id);
    String getClubNameById(@Param("clubID")int id);
    int getClubNumById(@Param("clubID")int id);

}
