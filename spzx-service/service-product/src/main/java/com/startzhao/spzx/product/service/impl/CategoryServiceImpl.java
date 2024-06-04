package com.startzhao.spzx.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.product.mapper.CategoryMapper;
import com.startzhao.spzx.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: CategoryService
 * Package: com.startzhao.spzx.product.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/4 17:03
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
}