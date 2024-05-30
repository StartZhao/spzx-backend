package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.ProductSkuMapper;
import com.startzhao.spzx.manager.service.ProductSkuService;
import com.startzhao.spzx.model.entity.product.ProductSku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: ProductSku
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/30 18:19
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements ProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;
}