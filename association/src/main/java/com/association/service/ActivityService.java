package com.association.service;

import com.association.Dao.ActivityDao;
import com.association.entity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    ActivityDao activityDao;

    public List<Activity> getActivityByClubID(int cid){
        return activityDao.getActivityByClubID(cid);
    }
    public boolean WriteActivity(Activity activity){return activityDao.WriteActivity(activity);}
//    public List<Activity> getAllActivityList(){return activityDao.getAllActivityList();}
    public int lastNumOfActivityID(){return activityDao.getAllActivityList().get(activityDao.getAllActivityList().size()-1).getActivityID()+1;}
    public List<Activity> getActivityByState(int state){return activityDao.getActivityByState(state);}
    public int activityNum(int state){return activityDao.getActivityByState(state).size();}
}
