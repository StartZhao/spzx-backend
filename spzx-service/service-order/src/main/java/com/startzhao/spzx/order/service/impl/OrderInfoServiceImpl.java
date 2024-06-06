package com.startzhao.spzx.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.feign.cart.CartFeignClient;
import com.startzhao.spzx.feign.product.ProductFeignClient;
import com.startzhao.spzx.feign.user.UserFeignClient;
import com.startzhao.spzx.model.dto.h5.OrderInfoDTO;
import com.startzhao.spzx.model.entity.h5.CartInfo;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.entity.order.OrderItem;
import com.startzhao.spzx.model.entity.order.OrderLog;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.model.entity.user.UserAddress;
import com.startzhao.spzx.model.entity.user.UserInfo;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.h5.TradeVo;
import com.startzhao.spzx.order.mapper.OrderInfoMapper;
import com.startzhao.spzx.order.mapper.OrderItemMapper;
import com.startzhao.spzx.order.mapper.OrderLogMapper;
import com.startzhao.spzx.order.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.security.auth.callback.Callback;
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
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderLogMapper orderLogMapper;

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

    /**
     * 下单
     *
     * @param orderInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitOrder(OrderInfoDTO orderInfoDTO) {
        // 1.校验库存是否充足
        List<OrderItem> orderItemList = orderInfoDTO.getOrderItemList();
        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new StartZhaoException(ResultCodeEnum.DATA_ERROR);
        }
        orderItemList.forEach(orderItem -> {
            Long skuId = orderItem.getSkuId();
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            if (productSku == null) {
                throw new StartZhaoException(ResultCodeEnum.DATA_ERROR);
            }
            if (productSku.getStockNum() < orderItem.getSkuNum()) {
                throw new StartZhaoException(ResultCodeEnum.STOCK_LESS);
            }
        });
        // 2.构建订单数据
        OrderInfo orderInfo = new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        orderInfo.setUserId(userInfo.getId());
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDTO.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDTO.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        // 3.将订单数据存入数据库
        orderInfoMapper.insert(orderInfo);
        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.insert(orderLog);

        // 远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();
        return orderInfo.getId();
    }

    /**
     * 立即购买
     *
     * @param skuId
     * @return
     */
    @Override
    public TradeVo buy(Long skuId) {
        // 查询商品
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        // 计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        // 返回
        return tradeVo;
    }

    /**
     * 获取订单分页列表
     *
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.lambda()
                .eq(OrderInfo::getUserId,userId)
                .eq(OrderInfo::getOrderStatus,orderStatus);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectList(orderInfoQueryWrapper);

        orderInfoList.forEach(orderInfo -> {
            QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
            orderItemQueryWrapper.lambda()
                    .eq(OrderItem::getOrderId,orderInfo.getId());
            List<OrderItem> orderItem = orderItemMapper.selectList(orderItemQueryWrapper);
            orderInfo.setOrderItemList(orderItem);
        });

        return new PageInfo<>(orderInfoList);
    }


}