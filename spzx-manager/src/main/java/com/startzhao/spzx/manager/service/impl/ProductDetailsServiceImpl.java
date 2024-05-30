package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.ProductDetailsMapper;
import com.startzhao.spzx.manager.service.ProductDetailsService;
import com.startzhao.spzx.model.entity.product.ProductDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: ProductDetails
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/30 18:22
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductDetailsServiceImpl extends ServiceImpl<ProductDetailsMapper, ProductDetails> implements ProductDetailsService {

    @Autowired
    private ProductDetailsMapper productDetailsMapper;
}