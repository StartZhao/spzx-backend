package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.OrderInfoService;
import com.startzhao.spzx.model.dto.h5.OrderInfoDTO;
import com.startzhao.spzx.model.dto.order.OrderStatisticsDTO;
import com.startzhao.spzx.model.entity.order.OrderStatistics;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.order.OrderStatisticsVO;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: OrderInfo
 * Package: com.startzhao.spzx.manager.task
 *
 * @Author StartZhao
 * @Create 2024/5/31 0:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVO> getOrderStatisticsData(OrderStatisticsDTO orderStatisticsDTO) {
        OrderStatisticsVO orderStatisticsVO = orderInfoService.getOrderStatisticsData(orderStatisticsDTO);
        return Result.build(orderStatisticsVO, ResultCodeEnum.SUCCESS);
    }


}