package com.startzhao.spzx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.model.vo.h5.IndexVO;
import com.startzhao.spzx.product.service.CategoryService;
import com.startzhao.spzx.product.service.IndexService;
import com.startzhao.spzx.product.service.ProductSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: Index
 * Package: com.startzhao.spzx.product.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/4 16:36
 * @Version 1.0
 */
@Service
@Slf4j
public class IndexServiceImpl implements IndexService {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductSkuService productSkuService;
    /**
     * 组装 IndexVO属性
     *
     * @return
     */
    @Override
    public IndexVO findData() {

        // 1.获取一级分类
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.lambda()
                .eq(Category::getParentId,0)
                .eq(Category::getStatus,1)
                .eq(Category::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
        List<Category> categoryList = categoryService.list(categoryQueryWrapper);

        // 2.从product_sku表获取排名前十的畅销商品
        QueryWrapper<ProductSku> productSkuQueryWrapper = new QueryWrapper<>();
        productSkuQueryWrapper.lambda()
                .eq(ProductSku::getStatus,1)
                .eq(ProductSku::getIsDeleted, DeletedStatusConstant.NOT_DELETED)
                .orderByDesc(ProductSku::getSaleNum);
        Page<ProductSku> page = new Page<>(1,10);
        List<ProductSku> productSkuList = productSkuService.list(page, productSkuQueryWrapper);
        IndexVO indexVO = new IndexVO();
        indexVO.setCategoryList(categoryList);
        indexVO.setProductSkuList(productSkuList);
        return indexVO;
    }
}