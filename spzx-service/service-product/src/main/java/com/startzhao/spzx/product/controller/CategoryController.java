package com.startzhao.spzx.product.controller;

import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: Category
 * Package: com.startzhao.spzx.product.controller
 *
 * @Author StartZhao
 * @Create 2024/6/4 19:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findCategoryTree")
    public Result<List<Category>> findCategoryTree() {
        List<Category> categoryList =  categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}