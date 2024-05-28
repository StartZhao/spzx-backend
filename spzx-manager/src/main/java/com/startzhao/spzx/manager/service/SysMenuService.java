package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.system.AssginRoleDTO;
import com.startzhao.spzx.model.dto.system.LoginDTO;
import com.startzhao.spzx.model.dto.system.SysUserDTO;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.system.LoginVO;

import java.util.List;

/**
 * ClassName: SysMenuService
 * Package: com.startzhao.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:58
 * @Version 1.0
 */
public interface SysMenuService extends IService<SysMenu> {


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

    /**
     * 根据用户id分配角色
     * @param assginRoleDTO
     * @return
     */
    Result doAssign(AssginRoleDTO assginRoleDTO);

    /**
     * 菜单分页
     *
     * @return
     */
    Result<List<SysMenu>> findNodes();
}
