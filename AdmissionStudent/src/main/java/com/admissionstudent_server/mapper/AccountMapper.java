package com.admissionstudent_server.mapper;

import com.admissionstudent_pojo.dto.UserSignUpDTO;
import com.admissionstudent_pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select username from user where username = #{username}")
    String selectUsername(String username);


    void insertAccount(UserSignUpDTO accountsignupdto);

    /**
     * 通过用户名获取账户所有信息
     */
    @Select("select * from user where username = #{username}")
    User getUserAllInfo(String username);

    /**
     * 将用户id插入到_user_student表中
     */
    @Insert("INSERT INTO _user_student(user_id) VALUES(#{userId})")
    void insertUserIdToUserStudent(Long userId);
}
