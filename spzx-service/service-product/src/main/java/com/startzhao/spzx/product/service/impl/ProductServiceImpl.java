package com.startzhao.spzx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.h5.ProductSkuDTO;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.product.mapper.ProductMapper;
import com.startzhao.spzx.product.mapper.ProductSkuMapper;
import com.startzhao.spzx.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: Product
 * Package: com.startzhao.spzx.product.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/4 23:30
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;



    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @param productDTO
     * @return
     */
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDTO productDTO) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productDTO);
        return new PageInfo<>(productSkuList);
    }
}