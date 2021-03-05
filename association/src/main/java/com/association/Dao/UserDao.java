package com.association.Dao;

import com.association.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User getUserByLogin(@Param("studentID") double id, @Param("userPassword") String pw);
    User getUserById(@Param("studentID") double id);
    User getMapByID(@Param("studentID") double id);
    String chackUserAuthInfo(@Param("studentID") double id);
    User getUserByName(@Param("userName")String name);
}
