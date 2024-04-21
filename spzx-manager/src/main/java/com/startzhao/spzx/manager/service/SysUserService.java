package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.system.LoginVO;
import com.startzhao.spzx.model.dto.system.LoginDTO;

/**
 * ClassName: SysUserServiceImpl
 * Package: com.startzhao.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:58
 * @Version 1.0
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录业务
     * @param loginDTO
     * @return
     */
    LoginVO login(LoginDTO loginDTO);


    /**
     * 实现账号退出即将 token 从缓存中删去
     * @param token
     */
    void logout(String token);
}
