package com.admissionstudent_server.service;

import com.admissionstudent_pojo.dto.StudentDTO;

public interface StudentService {
    /**
     * 添加学生信息
     */
    void addStudentInfo(StudentDTO studentdto);
}
