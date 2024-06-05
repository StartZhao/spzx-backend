package com.startzhao.spzx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: ProductDetailsMapper
 * Package: com.startzhao.spzx.product.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 9:41
 * @Version 1.0
 */
@Mapper
public interface ProductDetailsMapper extends BaseMapper<ProductDetails> {
}
