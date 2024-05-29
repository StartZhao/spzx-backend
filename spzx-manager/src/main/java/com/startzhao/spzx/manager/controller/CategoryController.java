package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.CategoryService;
import com.startzhao.spzx.model.entity.product.Category;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.product.CategoryExcelVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName: CategoryController
 * Package: com.startzhao.spzx.manager.controller
 * Description: 商品分类相关接口
 *
 * @Author StartZhao
 * @Create 2024/5/29 19:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findByParentId/{parentId}")
    public Result<List<Category>> findByParentId(@PathVariable Long parentId) {
        List<Category> categories = categoryService.findByParentId(parentId);
        return Result.build(categories, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
