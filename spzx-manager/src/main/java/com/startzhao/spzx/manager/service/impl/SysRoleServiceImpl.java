package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.mapper.SysRoleMapper;
import com.startzhao.spzx.manager.mapper.SysRoleMenuMapper;
import com.startzhao.spzx.manager.mapper.SysRoleUserMapper;
import com.startzhao.spzx.manager.service.SysRoleMenuService;
import com.startzhao.spzx.manager.service.SysRoleService;
import com.startzhao.spzx.model.dto.system.AssginMenuDTO;
import com.startzhao.spzx.model.dto.system.SysRoleDTO;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.entity.system.SysRoleMenu;
import com.startzhao.spzx.model.entity.system.SysRoleUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;


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
     */
    @Override
    public void saveSysRole(SysRole sysRole) {
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(roleCode)) {
            throw new StartZhaoException(500,"角色名或角色代码未填写，请填写完整");
        }
        sysRole.setCreateTime(DateTime.now());
        sysRole.setUpdateTime(DateTime.now());
        sysRoleMapper.insert(sysRole);
    }

    /**
     * 修改角色
     *
     * @param sysRole
     */
    @Override
    public void updateSysRole(SysRole sysRole) {
        String roleName = sysRole.getRoleName();
        String roleCode = sysRole.getRoleCode();
        if (StringUtils.isEmpty(roleName) || StringUtils.isEmpty(roleCode)) {
            throw new StartZhaoException(500, "角色名或角色编码为空");
        }
        sysRole.setUpdateTime(DateTime.now());
        sysRoleMapper.updateById(sysRole);
    }

    /**
     * 删除角色信息
     * 由于存在用户与角色信息相关联的情况
     * 故要在角色与用户没有关联时才可删除
     *
     * @param roleId
     */
    @Override
    public void deleteSysRole(Long roleId) {

        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId).eq("is_deleted", 0);
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(queryWrapper);
        if (sysRoleUsers.size() > 0) throw new StartZhaoException(500, "该角色被用户关联，若要删除该角色，请先取消用户与角色关联关系");
        sysRoleMapper.deleteById(roleId);
    }

    /**
     * 1、查询所有角色
     * 2、根据用户id得到用户对应角色id
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, List> findAllRoles(Long userId) {
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
        return data;
    }

    /**
     * 分配菜单
     * 1.删除原先角色菜单关系
     * 2.添加现有关系,分半开关系区别插入
     *
     * @param assignMenuDTO
     */
    @Override
    @Transactional
    public void doAssign(AssginMenuDTO assignMenuDTO) {
        Long roleId = assignMenuDTO.getRoleId();
        sysRoleMenuMapper.deleteByRoleId(roleId);

        List<Map<String, Long>> menuIdList = assignMenuDTO.getMenuIdList();
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        menuIdList.forEach(map -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(map.get("id"));
            sysRoleMenu.setIsHalf(map.get("isHalf"));
            sysRoleMenu.setCreateTime(DateTime.now());
            sysRoleMenu.setUpdateTime(DateTime.now());
            sysRoleMenus.add(sysRoleMenu);
        });
        sysRoleMenuService.saveBatch(sysRoleMenus, 100);
    }
}
