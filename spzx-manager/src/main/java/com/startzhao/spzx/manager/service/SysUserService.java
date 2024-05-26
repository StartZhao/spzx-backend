package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.system.SysUserDTO;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
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

    /**
     * 对系统用户进行分页查询
     * @param sysUserDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SysUser> findByPage(SysUserDTO sysUserDTO, Integer pageNum, Integer pageSize);

    /**
     * 保存系统用户
     * @param sysUser
     * @return
     */
    Result saveSysUser(SysUser sysUser);

    /**
     * 修改用户信息
     * @param sysUser
     * @return
     */
    Result updateSysUser(SysUser sysUser);

    /**
     * 根据id 删除用户数据
     * @param userId
     * @return
     */
    Result deleteSysUserById(Long userId);
}
