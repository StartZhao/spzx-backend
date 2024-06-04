package com.startzhao.spzx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.dto.h5.ProductSkuDTO;
import com.startzhao.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: ProductSkuMapper
 * Package: com.startzhao.spzx.product.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/4 17:07
 * @Version 1.0
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {



    List<ProductSku> findByPage(ProductSkuDTO productDTO);
}
