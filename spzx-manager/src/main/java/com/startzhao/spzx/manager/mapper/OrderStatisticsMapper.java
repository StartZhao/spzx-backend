package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.startzhao.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * ClassName: OrderStatisticsMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/31 0:01
 * @Version 1.0
 */
@Mapper
public interface OrderStatisticsMapper extends BaseMapper<OrderStatistics> {


}
