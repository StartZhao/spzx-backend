package com.startzhao.spzx.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.product.mapper.ProductSkuMapper;
import com.startzhao.spzx.product.service.ProductSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: ProductSku
 * Package: com.startzhao.spzx.product.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/4 17:06
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;
}