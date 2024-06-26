package com.startzhao.spzx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.h5.UserLoginDTO;
import com.startzhao.spzx.model.dto.h5.UserRegisterDTO;
import com.startzhao.spzx.model.entity.user.UserInfo;
import com.startzhao.spzx.model.vo.h5.UserInfoVO;

/**
 * ClassName: UserInfoService
 * Package: com.startzhao.spzx.user.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:41
 * @Version 1.0
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 得到当前登录用户的信息
     * @param token
     * @return
     */
    UserInfoVO getCurrentUserInfo(String token);
}
