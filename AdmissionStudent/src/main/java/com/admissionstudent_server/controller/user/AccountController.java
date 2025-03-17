package com.admissionstudent_server.controller.user;

import com.admissionstudent_common.result.Result;
import com.admissionstudent_pojo.dto.UserSignUpDTO;
import com.admissionstudent_server.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
//    @ApiOperation("注册功能")
    public Result<String> save(@RequestBody UserSignUpDTO userSignUpDTO) {
        log.info("注册账号，{}", userSignUpDTO);
        //查询是否有重复的账号
        String username = accountService.selectUsername(userSignUpDTO.getUsername());

        //注册失败
        if (username != null) {
            log.warn("账号已存在，注册失败：{}", userSignUpDTO.getUsername());
            return Result.error("账号已存在，注册失败");
        }

        //注册成功
        accountService.save(userSignUpDTO);

        log.info("注册成功：{}", userSignUpDTO.getUsername());
        return Result.success("注册成功");
    }
}
