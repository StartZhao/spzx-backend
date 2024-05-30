package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.manager.mapper.BrandMapper;
import com.startzhao.spzx.manager.service.BrandService;
import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.vo.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * ClassName: BrandServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 品牌业务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/5/30 8:22
 * @Version 1.0
 */
@Service
@Slf4j
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    /**
     * 分页显示
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageResult<Brand> getBrandPageList(Integer page, Integer limit) {
        IPage<Brand> brandPage = new Page<>(page,limit);
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Brand::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
        brandPage = brandMapper.selectPage(brandPage, queryWrapper);
        return PageResult.<Brand>builder()
                .records(brandPage.getRecords())
                .total(brandPage.getTotal()).build();
    }



}
