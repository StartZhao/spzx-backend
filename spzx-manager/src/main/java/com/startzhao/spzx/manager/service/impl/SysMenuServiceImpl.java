package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.mapper.SysMenuMapper;
import com.startzhao.spzx.manager.mapper.SysRoleUserMapper;
import com.startzhao.spzx.manager.mapper.SysUserMapper;
import com.startzhao.spzx.manager.service.SysMenuService;
import com.startzhao.spzx.manager.service.SysRoleUserService;
import com.startzhao.spzx.model.dto.system.AssginRoleDTO;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.entity.system.SysRoleUser;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysMenuServiceImpl
 * Package: com.startzhao.service.impl
 * Description: 首页服务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:58
 * @Version 1.0
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;





    /**
     * 保存系统用户
     *
     * @param sysUser
     * @return
     */
    @Override
    public Result saveSysUser(SysUser sysUser) {
        String userName = sysUser.getUserName();
        String password = sysUser.getPassword();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
            throw new StartZhaoException(500, "用户名或密码为空，请填写完整");

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        SysUser user = sysUserMapper.selectOne(queryWrapper);
        if (user != null) throw new StartZhaoException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        sysUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        sysUser.setStatus(0);
        sysUser.setCreateTime(DateTime.now());
        sysUser.setUpdateTime(DateTime.now());
        sysUserMapper.insert(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     */
    @Override
    public Result updateSysUser(SysUser sysUser) {
        sysUser.setUpdateTime(DateTime.now());
        sysUserMapper.updateById(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据id 删除用户数据
     *
     * @param userId
     * @return
     */
    @Override
    public Result deleteSysUserById(Long userId) {
        sysUserMapper.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据用户id分配角色
     * 1、逻辑删除表中原先用户角色关系
     * TODO：由于多次分配，可能导致一些逻辑删除操作越来越耗时，我想到的是定时物理删除这是已经逻辑删除的用户角色关系
     * 2、插入新的用户角色关系
     *
     * @param assginRoleDTO
     * @return
     */
    @Override
    @Transactional
    public Result doAssign(AssginRoleDTO assginRoleDTO) {
        Long userId = assginRoleDTO.getUserId();
        sysRoleUserMapper.deleteByUserId(userId);
        List<SysRoleUser> sysRoleUsers = new ArrayList<>();
        assginRoleDTO.getRoleIdList().forEach(roleId -> {
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userId);
            sysRoleUser.setRoleId(roleId);
            sysRoleUsers.add(sysRoleUser);
        });

        sysRoleUserService.saveBatch(sysRoleUsers,100);
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }

    /**
     * 菜单分页
     *
     * @return
     */
    @Override
    public Result<List<SysMenu>> findNodes() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", DeletedStatusConstant.FALSE);
        List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
        return Result.build(sysMenus,ResultCodeEnum.SUCCESS);
    }


}
