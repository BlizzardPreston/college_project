package com.association.service;

import com.association.emtity.user;
import org.springframework.data.repository.query.Param;


public interface ILoginService {
    user getuserbylogin( double id,String pw);
    user getuserbyid(double id);
    user getMapByID(double id);
    String chackUserAuthInfo(double id);
    user getUserByName(String name);
}
