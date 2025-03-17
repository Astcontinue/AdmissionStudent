package com.admissionstudent_server.controller.user;

import com.admissionstudent_common.constant.JwtClaimsConstant;
import com.admissionstudent_common.properties.JwtProperties;
import com.admissionstudent_common.result.Result;
import com.admissionstudent_common.utils.JwtUtil;
import com.admissionstudent_pojo.dto.UserLoginDTO;
import com.admissionstudent_pojo.entity.User;
import com.admissionstudent_pojo.vo.UserLoginVO;
import com.admissionstudent_server.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) throws AccountLockedException, AccountNotFoundException {
        log.info("员工登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
//    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 根据用户id查询用户所有信息
     */
    @GetMapping("/{id}")
    public Result<User> getUserProfile(@PathVariable String id) {
        User user = userService.getById(id);
        return Result.success(user);
    }
}
