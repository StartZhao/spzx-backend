package com.startzhao.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.startzhao.spzx.manager.mapper.OrderInfoMapper;
import com.startzhao.spzx.manager.mapper.OrderStatisticsMapper;
import com.startzhao.spzx.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: OrderStatisticsTask
 * Package: com.startzhao.spzx.manager.task
 * Description: 订单数据统计定时任务
 *
 * @Author StartZhao
 * @Create 2024/5/30 23:57
 * @Version 1.0
 */
@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        String createTime = DateUtil.offsetDay(new Date(), -1).toString(new SimpleDateFormat(("yyyy-MM-dd")));
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        if (orderStatistics != null) {
            orderStatisticsMapper.insert(orderStatistics);
        }
    }

}
