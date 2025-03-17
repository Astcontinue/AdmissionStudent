package com.admissionstudent_pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDTO implements Serializable {

    // 主键
    private Long id;

    // 学生姓名
    private String name;

    // 性别
    private String gender;

    // 出生日期
    private LocalDateTime dateOfBirth;

    // 照片
    private String photoUrl;

    // 身份证号码
    private String idcard;

    //户籍
    private String account;

    //电话1
    private String phonenumber1;

    //电话2
    private String phonenumber2;

    //住址
    private String address;

    //学历
    private String education;
}
