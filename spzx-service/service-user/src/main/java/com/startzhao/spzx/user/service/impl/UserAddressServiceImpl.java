package com.startzhao.spzx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.model.entity.user.UserAddress;
import com.startzhao.spzx.user.mapper.UserAddressMapper;
import com.startzhao.spzx.user.service.UserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: UserAddress
 * Package: com.startzhao.spzx.user.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:01
 * @Version 1.0
 */
@Service
@Slf4j
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 获取当前用户地址
     *
     * @return
     */
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        QueryWrapper<UserAddress> userAddressQueryWrapper = new QueryWrapper<>();
        userAddressQueryWrapper.lambda().eq(UserAddress::getUserId,userId);
        List<UserAddress> userAddressList = userAddressMapper.selectList(userAddressQueryWrapper);
        return userAddressList;
    }
}