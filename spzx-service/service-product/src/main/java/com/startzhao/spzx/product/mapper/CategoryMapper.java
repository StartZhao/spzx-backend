package com.startzhao.spzx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.startzhao.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: CategoryMapper
 * Package: com.startzhao.spzx.product.mapper
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/4 17:03
 * @Version 1.0
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
