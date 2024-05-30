package com.startzhao.spzx.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: CategoryBrandMapper
 * Package: com.startzhao.spzx.manager.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/30 15:06
 * @Version 1.0
 */
@Mapper
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {
}
