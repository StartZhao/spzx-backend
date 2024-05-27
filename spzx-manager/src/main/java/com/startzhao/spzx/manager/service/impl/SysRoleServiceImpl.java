package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.mapper.SysRoleMapper;
import com.startzhao.spzx.manager.mapper.SysRoleUserMapper;
import com.startzhao.spzx.manager.service.SysRoleService;
import com.startzhao.spzx.model.dto.system.SysRoleDTO;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.entity.system.SysRoleUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;


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
        queryWrapper.eq("is_deleted", 0).like("role_name", sysRoleDTO.getRoleName()).orderByDesc("id");
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
        sysRole.setCreateTime(DateTime.now());
        sysRole.setUpdateTime(DateTime.now());
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
            throw new StartZhaoException(500, "角色名或角色编码为空");
        }
        sysRole.setUpdateTime(DateTime.now());
        sysRoleMapper.updateById(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除角色信息
     * 由于存在用户与角色信息相关联的情况
     * 故要在角色与用户没有关联时才可删除
     *
     * @param roleId
     * @return
     */
    @Override
    public Result deleteSysRole(Long roleId) {

        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId).eq("is_deleted", 0);
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(queryWrapper);
        if (sysRoleUsers.size() > 0) throw new StartZhaoException(500, "该角色被用户关联，若要删除该角色，请先取消用户与角色关联关系");
        sysRoleMapper.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 1、查询所有角色
     * 2、根据用户id得到用户对应角色id
     *
     * @param userId
     * @return
     */
    @Override
    public Result<Map<String, List>> findAllRoles(Long userId) {
        Map<String, List> data = new HashMap<>();
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",0);
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        data.put("allRoleList", sysRoles);
        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("is_deleted", 0);
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(queryWrapper);
        List<Long> roleIds = new ArrayList<>();
        sysRoleUsers.forEach(sysRoleUser -> {
            roleIds.add(sysRoleUser.getRoleId());
        });
        data.put("userRoleIds", roleIds);
        return Result.build(data, ResultCodeEnum.SUCCESS);
    }
}
