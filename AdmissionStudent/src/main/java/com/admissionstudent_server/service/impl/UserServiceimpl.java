package com.admissionstudent_server.service.impl;


import com.admissionstudent_common.constant.MessageConstant;
import com.admissionstudent_common.constant.StatusConstant;
import com.admissionstudent_common.exception.PasswordErrorException;
import com.admissionstudent_pojo.dto.UserLoginDTO;
import com.admissionstudent_pojo.entity.User;
import com.admissionstudent_server.mapper.UserMapper;
import com.admissionstudent_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) throws AccountNotFoundException, AccountLockedException {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //1.根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
                if (user == null) {
                    //账号不存在
                    throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
                }

        //密码比对
        //md5加密方式
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }

    /**
     * 根据用户id查询用户所有信息
     */
    @Override
    public User getById(String id) {
        return userMapper.getById(id);
    }
}
