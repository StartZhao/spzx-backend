package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.system.SysRoleDTO;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.vo.common.PageResult;
import org.springframework.stereotype.Service;

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
}
