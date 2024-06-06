package com.startzhao.spzx.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.h5.OrderInfoDTO;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.vo.h5.TradeVo;

/**
 * ClassName: OrderInfoService
 * Package: com.startzhao.spzx.order.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:54
 * @Version 1.0
 */
public interface OrderInfoService extends IService<OrderInfo> {


    TradeVo getTrade();

    /**
     * 下单
     * @param orderInfoDTO
     * @return
     */
    Long submitOrder(OrderInfoDTO orderInfoDTO);

    /**
     * 立即购买
     * @param skuId
     * @return
     */
    TradeVo buy(Long skuId);

    /**
     * 获取订单分页列表
     * @param page
     * @param limit
     * @param orderStatus
     * @return
     */
    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);
}
