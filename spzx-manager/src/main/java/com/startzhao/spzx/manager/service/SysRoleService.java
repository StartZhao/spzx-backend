package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.system.AssginMenuDTO;
import com.startzhao.spzx.model.dto.system.SysRoleDTO;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.vo.common.PageResult;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SysRoleService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/23 17:47
 * @Version 1.0
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 系统角色分页
     *
     * @param sysRoleDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<SysRole> findByPage(SysRoleDTO sysRoleDTO, Integer pageNum, Integer pageSize);

    /**
     * 添加角色
     *
     * @param sysRole
     * @return
     */
    void saveSysRole(SysRole sysRole);

    /**
     * 修改角色
     *
     * @param sysRole
     * @return
     */
    void updateSysRole(SysRole sysRole);

    /**
     * 删除角色信息
     *
     * @param roleId
     */
    void deleteSysRole(Long roleId);

    /**
     * 查询所有角色
     *
     * @param userId
     * @return
     */
    Map<String,List> findAllRoles(Long userId);

    /**
     * 分配菜单
     *
     * @param assignMenuDTO
     */
    void doAssign(AssginMenuDTO assignMenuDTO);
}
