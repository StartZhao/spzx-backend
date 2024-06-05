package com.startzhao.spzx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.h5.UserRegisterDTO;
import com.startzhao.spzx.model.entity.user.UserInfo;

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
}
