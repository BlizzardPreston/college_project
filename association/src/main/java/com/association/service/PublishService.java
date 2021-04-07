package com.association.service;

import com.association.Dao.PublishDao;
import com.association.entity.Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishService {
    @Autowired
    private PublishDao publishDao;

    public Publish getPublishByID(long pid){
        return publishDao.getPublishByID(pid);
    }
    public int numOfPublish(){return publishDao.AllPublish().size();}
    public int lastNumOfpublish(){return publishDao.AllPublish().get(publishDao.AllPublish().size()-1).getPublishID()+1;}
    public boolean WritePublish(Publish publish){return publishDao.WritePublish(publish);}

}
