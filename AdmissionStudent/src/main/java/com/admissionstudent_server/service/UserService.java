package com.admissionstudent_server.service;

import com.admissionstudent_pojo.dto.UserLoginDTO;
import com.admissionstudent_pojo.entity.User;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;

public interface UserService {
    /**
     * 员工登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO) throws AccountNotFoundException, AccountLockedException;

    /**
     * 根据id查询用户所有信息
     */
    User getById(String id);
}
