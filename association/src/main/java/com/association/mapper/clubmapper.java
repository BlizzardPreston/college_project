package com.association.mapper;

import com.association.emtity.club;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface clubmapper {
    List<club> getallclub();
    club getclubbyid(int id);
    club getclubbyname(String name);
    club getclubbyAdmin(String admin);

}
