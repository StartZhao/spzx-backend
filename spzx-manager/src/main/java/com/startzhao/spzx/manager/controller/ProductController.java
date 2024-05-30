package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.ProductService;
import com.startzhao.spzx.manager.service.ProductUnitService;
import com.startzhao.spzx.model.dto.product.ProductDTO;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: Product
 * Package: com.startzhao.spzx.manager.controller
 *
 * @Author StartZhao
 * @Create 2024/5/30 16:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/product/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/{page}/{limit}")
    public Result<PageResult<Product>> getProductPageList(@PathVariable Integer page, @PathVariable Integer limit, ProductDTO productDTO) {
        PageResult<Product> pageResult = productService.getProductPageList(page,limit,productDTO);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        productService.save(product);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product){
        productService.updateById(product);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getById/{id}")
    public Result<Product> getById(@PathVariable Long id){
        Product product = productService.getById(id);
        return Result.build(product,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        productService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id,@PathVariable Long auditStatus) {
        productService.updateAuditStatus(id,auditStatus);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Long status) {
        productService.updateStatus(id,status);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}