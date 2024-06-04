package com.startzhao.spzx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: ProductMapper
 * Package: com.startzhao.spzx.product.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/4 23:30
 * @Version 1.0
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
