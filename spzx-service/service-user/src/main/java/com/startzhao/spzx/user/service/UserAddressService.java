package com.startzhao.spzx.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * ClassName: UserAddressService
 * Package: com.startzhao.spzx.user.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:00
 * @Version 1.0
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 获取用户地址
     * @return
     */
    List<UserAddress> findUserAddressList();
}
