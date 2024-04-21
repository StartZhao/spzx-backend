package com.startzhao.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.mapper.SysUserMapper;
import com.startzhao.spzx.manager.service.SysUserService;
import com.startzhao.spzx.model.dto.system.LoginDTO;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.system.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
    private RedisTemplate redisTemplate;

    /**
     * 用户登录业务
     * 1、根据用户名查找用户是否存在
     * 2、判断用户名与密码是否匹配
     * 3、生成 token 并且加入缓存中
     *
     * @param loginDTO
     * @return
     */
    @Override
    public LoginVO login(LoginDTO loginDTO) {
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
        redisTemplate.opsForValue().set("user:login" + token, JSON.toJSONString(sysUser), 30, TimeUnit.MINUTES);

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setRefresh_token("");
        return loginVO;
    }
}
