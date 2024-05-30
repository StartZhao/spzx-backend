package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.ProductUnitMapper;
import com.startzhao.spzx.manager.service.ProductUnitService;
import com.startzhao.spzx.model.entity.base.ProductUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: ProductUnit
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/30 17:00
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductUnitServiceImpl extends ServiceImpl<ProductUnitMapper, ProductUnit> implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

}