package com.startzhao.spzx.cart.service;

import com.startzhao.spzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * ClassName: CartService
 * Package: com.startzhao.spzx.cart.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 18:23
 * @Version 1.0
 */
public interface CartService {

    /**
     * 添加购物车
     * @param skuId
     * @param skuNum
     */
    void addToCart(Long skuId, Integer skuNum);

    /**
     * 购物车展示
     * @return
     */
    List<CartInfo> getCartList();

    /**
     * 删除购物车商品
     * @param skuId
     */
    void deleteCart(Long skuId);

    /**
     * 更新购物车选中状态
     * @param skuId
     * @param isChecked
     */
    void checkCart(Long skuId, Integer isChecked);

    /**
     * 全选购物车
     * @param isChecked
     */
    void allCheckCart(Integer isChecked);

    /**
     * 清空购物车
     */
    void clearCart();

    /**
     * 获得当前用户的所有购物车
     * @return
     */
    List<CartInfo> getAllCkecked();

    /**
     * 清空选中的购物车
     */
    void deleteChecked();
}
