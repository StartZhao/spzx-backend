package com.startzhao.spzx.cart.controller;

import com.startzhao.spzx.cart.service.CartService;
import com.startzhao.spzx.model.entity.h5.CartInfo;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: Cart
 * Package: com.startzhao.spzx.cart.controller
 *
 * @Author StartZhao
 * @Create 2024/6/5 18:22
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/order/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId, @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId,skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,@PathVariable Integer isChecked) {
        cartService.checkCart(skuId,isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable Integer isChecked) {
        cartService.allCheckCart(isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/clearCart")
    public Result clearCart() {
        cartService.clearCart();
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }



}