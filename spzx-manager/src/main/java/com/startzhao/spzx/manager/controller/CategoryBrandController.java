package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.CategoryBrandService;
import com.startzhao.spzx.model.dto.product.CategoryBrandDTO;
import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.entity.product.CategoryBrand;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: CategoryBrand
 * Package: com.startzhao.spzx.manager.controller
 * Description: 品牌分类相关接口
 *
 * @Author StartZhao
 * @Create 2024/5/30 14:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @GetMapping("/{page}/{limit}")
    public Result<PageResult<CategoryBrand>> getCategoryBrandPageList(@PathVariable Integer page, @PathVariable Integer limit, CategoryBrandDTO categoryBrandDTO) {
        PageResult<CategoryBrand> pageResult = categoryBrandService.getCategoryBrandPageList(page,limit,categoryBrandDTO);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        categoryBrandService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result<List<Brand>>findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> brandList = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList,ResultCodeEnum.SUCCESS);
    }
}