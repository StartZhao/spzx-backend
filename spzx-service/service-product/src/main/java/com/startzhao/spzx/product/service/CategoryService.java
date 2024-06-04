package com.startzhao.spzx.product.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.entity.product.Category;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * ClassName: CategoryService
 * Package: com.startzhao.spzx.product.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/4 17:02
 * @Version 1.0
 */
public interface CategoryService extends IService<Category> {


    /**
     * 对所有一级分类进行缓存
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return
     */
    @Override
    List<Category> list(Wrapper<Category> queryWrapper);
    /**
     * 得到category并且构建category树
     * @return
     */
    List<Category> findCategoryTree();
}
