package com.startzhao.spzx.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "统计结果实体类")
@Builder
public class OrderStatisticsVO {

    @Schema(description = "日期数据集合")
    private List<String> dateList ;

    @Schema(description = "总金额数据集合")
    private List<BigDecimal> amountList ;
}
