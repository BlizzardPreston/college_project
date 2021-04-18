package com.association.Dao;

import com.association.entity.Activity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityDao {
    List<Activity> getActivityByClubID(@Param("clubID")int cid);
    boolean WriteActivity(@Param("activity")Activity activity);
    List<Activity> getAllActivityList();
    List<Activity> getActivityByState(@Param("state")int state);
}
