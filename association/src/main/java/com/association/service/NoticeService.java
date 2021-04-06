package com.association.service;

import com.association.Dao.NoticeDao;
import com.association.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    public boolean WriteNotice(Notice notice){return noticeDao.WriteNotice(notice);};
    public List<Notice> AllNotice(){return noticeDao.AllNotice();};
    public Notice getNoticeByClubID(int cid){
        return noticeDao.getNoticeByClubID(cid);
    }
    public boolean deleteNoticeByNoticeID(long nid){
        return noticeDao.deleteNoticeByNoticeID(nid);
    }
//    获得最新通知
    public long lastNumOfNoticeID(){return noticeDao.AllNotice().get(noticeDao.AllNotice().size()-1).getNoticeID()+1;}
}
