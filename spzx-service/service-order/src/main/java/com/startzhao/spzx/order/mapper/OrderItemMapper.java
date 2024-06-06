package com.startzhao.spzx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.startzhao.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: OrderItemMapper
 * Package: com.startzhao.spzx.order.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/6 19:57
 * @Version 1.0
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
