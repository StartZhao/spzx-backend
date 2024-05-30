package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.order.OrderInfo;
import com.startzhao.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * ClassName: OrderInfoMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/30 23:59
 * @Version 1.0
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {


    /**
     * 查询指定日期产生的订单数据
     * @param creatTime
     * @return
     */
    @Select("select DATE_FORMAT(oi.create_time ,'%Y-%m-%d') orderDate, sum(oi.total_amount)  totalAmount , count(oi.id) totalNum\n" +
            "        from order_info oi where DATE_FORMAT(oi.create_time ,'%Y-%m-%d') = #{createTime}\n" +
            "        GROUP BY DATE_FORMAT(oi.create_time ,'%Y-%m-%d')")
    OrderStatistics selectOrderStatistics(String creatTime);
}


