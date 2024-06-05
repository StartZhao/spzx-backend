package com.startzhao.spzx.product.controller;

import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.h5.ProductSkuDTO;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.h5.ProductItemVO;
import com.startzhao.spzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * ClassName: Product
 * Package: com.startzhao.spzx.product.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/4 23:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<ProductSku>> findByPage(@PathVariable Integer page, @PathVariable Integer limit, ProductSkuDTO productDTO) {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page,limit,productDTO);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/item/{skuId}")
    public Result<ProductItemVO> item(@PathVariable Long skuId) {
        ProductItemVO productItemVO = productService.item(skuId);
        return Result.build(productItemVO,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId) {
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }
}