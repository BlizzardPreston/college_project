package com.association.mapper;

import com.association.emtity.user;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;



@Mapper
//查询user表
public interface LoginMapper {
   user getuserbylogin(@Param("studentID") double id,@Param("password") String pw);
   user getuserbyid(@Param("studentID") double id);
   user getMapByID(@Param("studentID") double id);
   String chackUserAuthInfo(@Param("studentID") double id);
   user getUserByName(@Param("userName")String name);

}
