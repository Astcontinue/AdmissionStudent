package com.admissionstudent_server.mapper;

import com.admissionstudent_pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);

    /**
     * 根据用户id查询用户的所有信息
     */
    @Select("select * from user where id = #{id}")
    User getById(String id);
}
