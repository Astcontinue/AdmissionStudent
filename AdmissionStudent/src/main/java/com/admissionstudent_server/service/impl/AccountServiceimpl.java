package com.admissionstudent_server.service.impl;

import com.admissionstudent_pojo.dto.UserSignUpDTO;
import com.admissionstudent_pojo.entity.User;
import com.admissionstudent_server.mapper.AccountMapper;
import com.admissionstudent_server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class AccountServiceimpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 注册账号
     *
     * @param userSignUpDTO
     */
    @Override
    public void save(UserSignUpDTO userSignUpDTO) {
        // 加密密码
        String encryptedPwd = DigestUtils.md5DigestAsHex(userSignUpDTO.getPassword().getBytes());
        userSignUpDTO.setPassword(encryptedPwd);

        //自动插入创建账号时间
        userSignUpDTO.setCreateTime(LocalDateTime.now());

        System.out.println("[DEBUG] 设置的时间戳：" + userSignUpDTO.getCreateTime()); // 验证是否成功赋值
        accountMapper.insertAccount(userSignUpDTO);

        //成功插入数据后，将该条数据额id一同插入到_user_student表中
        User userAllInfo = accountMapper.getUserAllInfo(userSignUpDTO.getUsername());
        if (userAllInfo.getId() > 0) {
            accountMapper.insertUserIdToUserStudent(userAllInfo.getId());
        }
    }

    /**
     * 查询是否有该用户名
     *
     */
    @Override
    public String selectUsername(String username) {
        return accountMapper.selectUsername(username);
    }
}
