package com.admissionstudent_server.service.impl;


import com.admissionstudent_common.constant.MessageConstant;
import com.admissionstudent_common.constant.StatusConstant;
import com.admissionstudent_common.exception.PasswordErrorException;
import com.admissionstudent_pojo.dto.EmployeeLoginDTO;
import com.admissionstudent_pojo.entity.Employee;
import com.admissionstudent_server.mapper.EmployeeMapper;
import com.admissionstudent_server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;

@Service
public class EmployeeServiceimpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) throws AccountNotFoundException, AccountLockedException {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1.根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
                if (employee == null) {
                    //账号不存在
                    throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
                }

        //密码比对
        //md5加密方式
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
}
