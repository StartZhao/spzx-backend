package com.startzhao.spzx.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
