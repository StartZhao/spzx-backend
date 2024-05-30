package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.product.CategoryBrandDTO;
import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.entity.product.CategoryBrand;
import com.startzhao.spzx.model.vo.common.PageResult;

import java.util.List;

/**
 * ClassName: CategoryBrandService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/30 15:04
 * @Version 1.0
 */
public interface CategoryBrandService extends IService<CategoryBrand> {

    /**
     * 通过品牌和分类进行分页查询
     *
     * @param page
     * @param limit
     * @param categoryBrandDTO
     * @return
     */
    PageResult<CategoryBrand> getCategoryBrandPageList(Integer page, Integer limit, CategoryBrandDTO categoryBrandDTO);

    /**
     * 根据分类id获取品牌数据
     * @param categoryId
     * @return
     */
    List<Brand> findBrandByCategoryId(Long categoryId);
}
