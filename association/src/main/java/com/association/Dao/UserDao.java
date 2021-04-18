package com.association.Dao;

import com.association.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User getUserByLogin(@Param("studentID") long id, @Param("userPassword") String pw);
    User getUserById(@Param("studentID") long id);
    User getMapByID(@Param("studentID") long id);
    String chackUserAuthInfo(@Param("studentID") long id);
    User getUserByName(@Param("userName")String name);
    boolean addUser(User user);
    boolean updataClubID(@Param("clubID") int cid,@Param("studentID")long sid);
}
