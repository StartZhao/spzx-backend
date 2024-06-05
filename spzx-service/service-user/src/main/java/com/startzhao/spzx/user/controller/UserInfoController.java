package com.startzhao.spzx.user.controller;

import com.startzhao.spzx.model.dto.h5.UserLoginDTO;
import com.startzhao.spzx.model.dto.h5.UserRegisterDTO;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.h5.UserInfoVO;
import com.startzhao.spzx.user.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: UserInfo
 * Package: com.startzhao.spzx.user.controller
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        userInfoService.register(userRegisterDTO);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = userInfoService.login(userLoginDTO);
        return Result.build(token,ResultCodeEnum.SUCCESS);
    }


    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfoVO> getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVO userInfoVO = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVO,ResultCodeEnum.SUCCESS);
    }


}