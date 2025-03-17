package com.admissionstudent_server.service;

import com.admissionstudent_pojo.dto.UserSignUpDTO;

public interface AccountService {
    void save(UserSignUpDTO userSignUpDTO);

    String selectUsername(String username);
}
