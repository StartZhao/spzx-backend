package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.order.OrderStatisticsDTO;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.vo.order.OrderStatisticsVO;

/**
 * ClassName: OrderInfoService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/31 0:18
 * @Version 1.0
 */
public interface OrderInfoService extends IService<OrderInfo> {



    /**
     * 根据日期获取订单统计数据
     *
     * @param orderStatisticsDTO
     * @return
     */
    OrderStatisticsVO getOrderStatisticsData(OrderStatisticsDTO orderStatisticsDTO);
}
