package com.admissionstudent_server.mapper;

import com.admissionstudent_pojo.dto.UserSignUpDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select username from user where username = #{username}")
    String selectUsername(String username);


    void insertAccount(UserSignUpDTO accountsignupdto);
}
