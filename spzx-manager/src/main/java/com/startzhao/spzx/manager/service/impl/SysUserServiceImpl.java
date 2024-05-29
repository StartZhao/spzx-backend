package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.mapper.SysRoleUserMapper;
import com.startzhao.spzx.manager.mapper.SysUserMapper;
import com.startzhao.spzx.manager.service.SysRoleUserService;
import com.startzhao.spzx.manager.service.SysUserService;
import com.startzhao.spzx.model.dto.system.AssginRoleDTO;
import com.startzhao.spzx.model.dto.system.LoginDTO;
import com.startzhao.spzx.model.dto.system.SysUserDTO;
import com.startzhao.spzx.model.entity.system.SysRoleUser;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.system.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SysUserServiceImpl
 * Package: com.startzhao.service.impl
 * Description: 首页服务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:58
 * @Version 1.0
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录业务
     * <p>
     * 1、校验验证码是否正确
     * 2、根据用户名查找用户是否存在
     * 3、判断用户名与密码是否匹配
     * 4、生成 token 并且加入缓存中
     *
     * @param loginDTO
     * @return
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {

        // 校验验证码
        String captcha = loginDTO.getCaptcha();
        String codeKey = loginDTO.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:login:validateCode:" + codeKey);
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)) {
            throw new StartZhaoException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //TODO 思考是否需要将其验证码在校验通过后就全部删除
        redisTemplate.delete("user:login:validateCode:" + codeKey);

        String userName = loginDTO.getUserName();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            throw new StartZhaoException(201, "用户不存在，请重新输入");
        }
        String password = loginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(sysUser.getPassword())) {
            throw new StartZhaoException(201, "密码错误，请重新输入");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setRefresh_token("");
        return loginVO;
    }


    /**
     * 实现账号退出即将 token 从缓存中删去
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    /**
     * 对系统用户进行分页查询
     *
     * @param sysUserDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<SysUser> findByPage(SysUserDTO sysUserDTO, Integer pageNum, Integer pageSize) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", sysUserDTO.getKeyword())
                .eq("is_deleted", 0)
                .orderByDesc("id");
        String createTimeBegin = sysUserDTO.getCreateTimeBegin();
        String createTimeEnd = sysUserDTO.getCreateTimeEnd();
        if (!StringUtils.isEmpty(createTimeBegin)) queryWrapper.ge("create_time", createTimeBegin);
        if (!StringUtils.isEmpty(createTimeEnd)) queryWrapper.le("create_time", createTimeEnd);
        page = sysUserMapper.selectPage(page, queryWrapper);
        log.info("SysUserServiceImpl.findByPage业务结束，结果{}", page.getRecords());
        return new PageResult<SysUser>(page.getTotal(), page.getRecords());
    }

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
            sysRoleUser.setCreateTime(DateTime.now());
            sysRoleUser.setUpdateTime(DateTime.now());
            sysRoleUsers.add(sysRoleUser);
        });

        sysRoleUserService.saveBatch(sysRoleUsers,100);
        return Result.build(null,ResultCodeEnum.SUCCESS);

    }


}
