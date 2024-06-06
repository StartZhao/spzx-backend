package com.startzhao.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.startzhao.spzx.cart.service.CartService;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.feign.product.ProductFeignClient;
import com.startzhao.spzx.model.entity.h5.CartInfo;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: Cart
 * Package: com.startzhao.spzx.cart.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/5 18:23
 * @Version 1.0
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    ProductFeignClient productFeignClient;

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    /**
     * 添加购物车
     * 要判断库存是否充足
     *
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {

        // 获取库存信息
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        Integer stockNum = productSku.getStockNum();

        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        // 获取缓存对象
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = null;


        // 商品已经在购物车
        if (cartInfoObj != null) {
            if (cartInfo.getSkuNum() + skuNum > stockNum) {
                throw new StartZhaoException(500, "库存不足");
            }
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            if (skuNum > stockNum) {
                throw new StartZhaoException(500, "库存不足");
            }
            // 商品不在购物车
            cartInfo = new CartInfo();

            // 购物车数据是从商品详情得到 {skuInfo}，通过 feign 远程调用
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());

        }
        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));


    }

    /**
     * 购物车展示
     *
     * @return
     */
    @Override
    public List<CartInfo> getCartList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<Object> cartInfoList = redisTemplate.opsForHash().values(getCartKey(userId));
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            List<CartInfo> infoList = cartInfoList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())
                    ).collect(Collectors.toList());
            return infoList;
        }


        return null;
    }

    /**
     * 删除购物车商品
     *
     * @param skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        // 删除购物车对象
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));
    }

    /**
     * 更新购物车选中状态
     *
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if (hasKey) {
            String cartInfoJSON = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            CartInfo cartInfo = JSON.parseObject(cartInfoJSON, CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
        }
    }

    /**
     * 全选购物车
     *
     * @param isChecked
     */
    @Override
    public void allCheckCart(Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            cartInfoList.stream().map(cartInfoJSON -> {
                CartInfo cartInfo = JSON.parseObject(cartInfoJSON.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo;
            }).forEach(cartInfo ->
                    redisTemplate.opsForHash().put(cartKey, String.valueOf(cartInfo.getId()), JSON.toJSONString(cartInfo))
            );
        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    /**
     * 获得当前用户的所有购物车
     *
     * @return
     */
    @Override
    public List<CartInfo> getAllCkecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        if (CollectionUtils.isEmpty(cartInfoList)) {
            return null;
        }
        List<CartInfo> cartInfos = cartInfoList.stream()
                .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .collect(Collectors.toList());
        return cartInfos;
    }

    /**
     * 清空选中的购物车
     */
    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 删除选中的购物项数据
        if (!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey, String.valueOf(cartInfo.getSkuId())));
        }
    }
}