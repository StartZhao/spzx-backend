package com.startzhao.spzx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.model.dto.h5.UserLoginDTO;
import com.startzhao.spzx.model.dto.h5.UserRegisterDTO;
import com.startzhao.spzx.model.entity.user.UserInfo;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.h5.UserInfoVO;
import com.startzhao.spzx.user.mapper.UserInfoMapper;
import com.startzhao.spzx.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: UserInfo
 * Package: com.startzhao.spzx.user.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:41
 * @Version 1.0
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户注册
     * TODO：短信验证码，等都做完了，在进行测试
     *
     * @param userRegisterDTO
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        // 获取数据
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String nickName = userRegisterDTO.getNickName();
        String code = userRegisterDTO.getCode();

        //校验参数
        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(code)) {
            throw new StartZhaoException(ResultCodeEnum.DATA_ERROR);
        }


        //校验校验验证码
        String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + username);
        if (!code.equals(codeValueRedis)) {
            throw new StartZhaoException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.lambda()
                .eq(UserInfo::getUsername, username);
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (null != userInfo) {
            throw new StartZhaoException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //保存用户信息
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.insert(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username);
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public String login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //校验参数
        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password)) {
            throw new StartZhaoException(ResultCodeEnum.DATA_ERROR);
        }

        // 校验用户名
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.lambda()
                .eq(UserInfo::getUsername, username);
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (userInfo == null) {
            throw new StartZhaoException(ResultCodeEnum.LOGIN_ERROR);
        }
        //校验密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        userInfoQueryWrapper.lambda()
                .eq(UserInfo::getPassword, md5InputPassword);
        userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (userInfo == null) {
            throw new StartZhaoException(ResultCodeEnum.LOGIN_ERROR);
        }

        //判断状态
        if (userInfo.getStatus() == 0) {
            throw new StartZhaoException(ResultCodeEnum.ACCOUNT_STOP);
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }

    /**
     * 得到当前登录用户的信息
     *
     * @param token
     * @return
     */
    @Override
    public UserInfoVO getCurrentUserInfo(String token) {

        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo,userInfoVO);
        return userInfoVO;
    }
}