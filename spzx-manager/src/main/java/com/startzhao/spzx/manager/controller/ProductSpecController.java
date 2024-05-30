package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.ProductSpecService;
import com.startzhao.spzx.model.entity.product.ProductSpec;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ProductSpec
 * Package: com.startzhao.spzx.manager.controller
 *
 * @Author StartZhao
 * @Create 2024/5/30 16:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService;

    @GetMapping("/{page}/{limit}")
    public Result<PageResult<ProductSpec>> getProductSpecPageList(@PathVariable Integer page, @PathVariable Integer limit) {
        PageResult<ProductSpec> pageResult = productSpecService.getProductSpecPageList(page,limit);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        productSpecService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result<List<ProductSpec>> findAll(){
        List<ProductSpec> list = productSpecService.list();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
}