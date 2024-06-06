package com.startzhao.spzx.order.controller;

import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.h5.OrderInfoDTO;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.h5.TradeVo;
import com.startzhao.spzx.order.service.OrderInfoService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: OrderInfo
 * Package: com.startzhao.spzx.order.controller
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @GetMapping("/auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/auth/submitOrder")
    public Result<Long> submitOrder(@RequestBody OrderInfoDTO orderInfoDTO) {
        Long orderId = orderInfoService.submitOrder(orderInfoDTO);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/buy/{skuId}")
    public Result<TradeVo> buy(@PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> list(@PathVariable Integer page, @PathVariable Integer limit,Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findUserPage(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}