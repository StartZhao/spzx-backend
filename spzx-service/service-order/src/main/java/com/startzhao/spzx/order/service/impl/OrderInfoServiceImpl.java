package com.startzhao.spzx.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.feign.cart.CartFeignClient;
import com.startzhao.spzx.model.entity.h5.CartInfo;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.entity.order.OrderItem;
import com.startzhao.spzx.model.vo.h5.TradeVo;
import com.startzhao.spzx.order.mapper.OrderInfoMapper;
import com.startzhao.spzx.order.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: OrderInfo
 * Package: com.startzhao.spzx.order.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:55
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private CartFeignClient cartFeignClient ;

    @Override
    public TradeVo getTrade() {

        // 获取选中的购物车数据
        List<CartInfo> cartInfoList = cartFeignClient.getAllCkecked();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {

            // 将购物项数据转换成功订单明细数据
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }

        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }
}