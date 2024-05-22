package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.SysRoleMapper;
import com.startzhao.spzx.manager.service.SysRoleService;
import com.startzhao.spzx.model.dto.system.SysRoleDTO;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SysRoleServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 系统角色业务具体类
 *
 * @Author StartZhao
 * @Create 2024/4/23 17:48
 * @Version 1.0
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * 系统角色分页
     *
     * @param sysRoleDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<SysRole> findByPage(SysRoleDTO sysRoleDTO, Integer pageNum, Integer pageSize) {
        IPage<SysRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("role_name", sysRoleDTO.getRoleName()).orderByDesc("id");
        page = sysRoleMapper.selectPage(page, queryWrapper);
        List<SysRole> sysRoleList = page.getRecords();
        long total = page.getTotal();
        log.info("分页成功" + sysRoleList);
        return new PageResult<SysRole>(total, sysRoleList);
    }

    /**
     * 添加角色
     *
     * @param sysRole
     * @return
     */
    @Override
    public Result saveSysRole(SysRole sysRole) {
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(roleCode)) {
            return Result.build(null, ResultCodeEnum.FAIL);
        }
        sysRoleMapper.insert(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改角色
     *
     * @param sysRole
     * @return
     */
    @Override
    public Result updateSysRole(SysRole sysRole) {
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(roleCode)) {
            return Result.build(null, ResultCodeEnum.FAIL);
        }
        sysRoleMapper.updateById(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public Result deleteSysRole(Long roleId) {
        sysRoleMapper.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
