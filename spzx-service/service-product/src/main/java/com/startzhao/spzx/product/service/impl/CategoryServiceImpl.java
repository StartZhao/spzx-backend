package com.startzhao.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.product.mapper.CategoryMapper;
import com.startzhao.spzx.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 得到category并且构建category树
     *
     * @return
     */
    @Cacheable(value = "category", key = "'all'")
    @Override
    public List<Category> findCategoryTree() {

        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.lambda()
                .eq(Category::getParentId, 0)
                .eq(Category::getStatus, 1)
                .eq(Category::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
        List<Category> categoryList = categoryMapper.selectList(categoryQueryWrapper);
        buildChildTree(categoryList);
        return categoryList;
    }

    /**
     * 构建下级分类树
     * TODO:可以看看能否优化成不需要回表查询
     * @param categoryList
     */
    private void buildChildTree(List<Category> categoryList) {
        if (categoryList == null) {
            return;
        }
        categoryList.forEach(category -> {
            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.lambda()
                    .eq(Category::getParentId, category.getId())
                    .eq(Category::getStatus, 1)
                    .eq(Category::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
            List<Category> list = categoryMapper.selectList(categoryQueryWrapper);
            category.setChildren(list);
            buildChildTree(list);
        });
    }

    /**
     * 对所有一级分类进行缓存
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return
     */
    @Override
    public List<Category> list(Wrapper<Category> queryWrapper) {
        String categoryListJSON = redisTemplate.opsForValue().get("category:one");
        if (!StringUtils.isEmpty(categoryListJSON)) {
            List<Category> categoryList = JSON.parseArray(categoryListJSON, Category.class);
            log.info("从Redis缓存中查询到了所有的一级分类数据");
            return categoryList ;
        }
        List<Category> list = super.list(queryWrapper);
        log.info("从数据库中查询到了所有的一级分类数据");
        redisTemplate.opsForValue().set("category:one" , JSON.toJSONString(list) , 7 , TimeUnit.DAYS);
        return list ;
    }


}