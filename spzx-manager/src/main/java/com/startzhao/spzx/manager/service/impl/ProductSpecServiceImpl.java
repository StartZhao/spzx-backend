package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.manager.mapper.ProductSpecMapper;
import com.startzhao.spzx.manager.service.ProductSpecService;
import com.startzhao.spzx.model.entity.product.ProductSpec;
import com.startzhao.spzx.model.entity.product.ProductSpec;
import com.startzhao.spzx.model.vo.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: ProductSpec
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/30 16:03
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    /**
     * 分页显示
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageResult<ProductSpec> getProductSpecPageList(Integer page, Integer limit) {
        IPage<ProductSpec> productSpecPage = new Page<>(page,limit);
        QueryWrapper<ProductSpec> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProductSpec::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
        productSpecPage = productSpecMapper.selectPage(productSpecPage, queryWrapper);
        return PageResult.<ProductSpec>builder()
                .records(productSpecPage.getRecords())
                .total(productSpecPage.getTotal()).build();
    }
}