package com.admissionstudent_server.service;

import com.admissionstudent_pojo.dto.EmployeeLoginDTO;
import com.admissionstudent_pojo.entity.Employee;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;

public interface EmployeeService {
    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO) throws AccountNotFoundException, AccountLockedException;
}
