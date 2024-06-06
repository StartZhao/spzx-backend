package com.startzhao.spzx.feign.cart;

import com.startzhao.spzx.model.entity.h5.CartInfo;
import com.startzhao.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ClassName: CartFeignClient
 * Package: com.startzhao.spzx.feign.cart
 * Description: 购物车远程调用接口
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:29
 * @Version 1.0
 */


@FeignClient(value = "service-cart")
public interface CartFeignClient
{
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    List<CartInfo> getAllCkecked() ;

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    Result deleteChecked() ;
}
