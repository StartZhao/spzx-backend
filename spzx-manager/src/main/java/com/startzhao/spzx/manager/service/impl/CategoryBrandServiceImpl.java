package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.manager.mapper.BrandMapper;
import com.startzhao.spzx.manager.mapper.CategoryBrandMapper;
import com.startzhao.spzx.manager.mapper.CategoryMapper;
import com.startzhao.spzx.manager.service.CategoryBrandService;
import com.startzhao.spzx.model.dto.product.CategoryBrandDTO;
import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.entity.product.CategoryBrand;
import com.startzhao.spzx.model.vo.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CategoryBrand
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/30 15:05
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryBrandServiceImpl extends ServiceImpl<CategoryBrandMapper, CategoryBrand> implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 通过品牌和分类进行分页查询
     *
     * @param page
     * @param limit
     * @param categoryBrandDTO
     * @return
     */
    @Override
    public PageResult<CategoryBrand> getCategoryBrandPageList(Integer page, Integer limit, CategoryBrandDTO categoryBrandDTO) {
        IPage<CategoryBrand> categoryBrandPage = new Page<>(page, limit);
        QueryWrapper<CategoryBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CategoryBrand::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
        Long brandId = categoryBrandDTO.getBrandId();
        Long categoryId = categoryBrandDTO.getCategoryId();
        if (brandId != null) {
            queryWrapper.lambda().eq(CategoryBrand::getBrandId, brandId);
        }
        if (categoryId != null) {
            queryWrapper.lambda().eq(CategoryBrand::getCategoryId, categoryId);
        }
        categoryBrandPage = categoryBrandMapper.selectPage(categoryBrandPage, queryWrapper);
        List<CategoryBrand> categoryBrandList = categoryBrandPage.getRecords();
        categoryBrandList.forEach(categoryBrand -> {
            categoryBrand.setCategoryName(categoryMapper.selectById(categoryBrand.getCategoryId()).getName());
            categoryBrand.setBrandName(brandMapper.selectById(categoryBrand.getBrandId()).getName());
            categoryBrand.setLogo(brandMapper.selectById(categoryBrand.getBrandId()).getLogo());
        });
        return PageResult.<CategoryBrand>builder()
                .records(categoryBrandList)
                .total(categoryBrandPage.getTotal()).build();
    }

    /**
     * 根据分类id获取品牌数据
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        QueryWrapper<CategoryBrand> categoryBrandQueryWrapper = new QueryWrapper<>();
        categoryBrandQueryWrapper.lambda().eq(CategoryBrand::getCategoryId, categoryId);
        List<CategoryBrand> categoryBrandList = list(categoryBrandQueryWrapper);
        List<Long> brandIdList = new ArrayList<>();
        categoryBrandList.forEach(categoryBrand -> {
            brandIdList.add(categoryBrand.getBrandId());
        });
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.lambda().in(Brand::getId, brandIdList);
        List<Brand> brandList = brandMapper.selectList(brandQueryWrapper);
        return brandList;
    }
}