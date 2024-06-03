package com.startzhao.spzx.manager.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.startzhao.spzx.common.log.annotation.Log;
import com.startzhao.spzx.common.log.enums.OperatorType;
import com.startzhao.spzx.manager.service.BrandService;
import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: BrandController
 * Package: com.startzhao.spzx.manager.controller
 * Description: 商品品牌管理相关接口
 *
 * @Author StartZhao
 * @Create 2024/5/30 8:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Log(title = "品牌列表",businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/{page}/{limit}")
    public Result<PageResult<Brand>> getBrandPageList(@PathVariable Integer page, @PathVariable Integer limit) {
        PageResult<Brand> pageResult = brandService.getBrandPageList(page,limit);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        brandService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result<List<Brand>> findAll(){
        List<Brand> list = brandService.list();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
}
