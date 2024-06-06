package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.OrderInfoMapper;
import com.startzhao.spzx.manager.mapper.OrderStatisticsMapper;
import com.startzhao.spzx.manager.service.OrderInfoService;
import com.startzhao.spzx.model.dto.h5.OrderInfoDTO;
import com.startzhao.spzx.model.dto.order.OrderStatisticsDTO;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.entity.order.OrderItem;
import com.startzhao.spzx.model.entity.order.OrderStatistics;
import com.startzhao.spzx.model.vo.order.OrderStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: OrderInfo
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/31 0:19
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;


    /**
     * 根据日期获取订单统计数据
     *
     * @param orderStatisticsDTO
     * @return
     */
    @Override
    public OrderStatisticsVO getOrderStatisticsData(OrderStatisticsDTO orderStatisticsDTO) {
        QueryWrapper<OrderStatistics> orderStatisticsQueryWrapper = new QueryWrapper<>();
        orderStatisticsQueryWrapper.lambda().orderByAsc(OrderStatistics::getOrderDate);
        if (!StringUtils.isEmpty(orderStatisticsDTO.getCreateTimeBegin())) {
            orderStatisticsQueryWrapper.lambda().ge(OrderStatistics::getOrderDate,orderStatisticsDTO.getCreateTimeBegin());
        }
        if (!StringUtils.isEmpty(orderStatisticsDTO.getCreateTimeEnd())) {
            orderStatisticsQueryWrapper.lambda().le(OrderStatistics::getOrderDate,orderStatisticsDTO.getCreateTimeEnd());
        }
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsQueryWrapper);
        List<String> dateList = orderStatisticsList.stream().map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd")).collect(Collectors.toList());
        List<BigDecimal> amountList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());
        OrderStatisticsVO orderStatisticsVO = OrderStatisticsVO.builder().dateList(dateList).amountList(amountList).build();
        return orderStatisticsVO;
    }


}