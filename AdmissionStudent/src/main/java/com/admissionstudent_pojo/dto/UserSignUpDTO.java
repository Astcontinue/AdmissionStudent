package com.admissionstudent_pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserSignUpDTO implements Serializable {

    //用户名
    private String username;

    //密码
    private String password;

    //住址
    private String company;

    //姓氏
    private String firstname;

    //名字
    private String lastname;

    //账号创建时间
    private LocalDateTime createTime;
}
