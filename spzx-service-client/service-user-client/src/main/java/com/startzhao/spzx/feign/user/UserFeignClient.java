package com.startzhao.spzx.feign.user;

import com.startzhao.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: UserFeignClient
 * Package: com.startzhao.spzx.feign
 * Description: 用户远程接口
 *
 * @Author StartZhao
 * @Create 2024/6/6 18:51
 * @Version 1.0
 */
@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable Long id) ;
}
