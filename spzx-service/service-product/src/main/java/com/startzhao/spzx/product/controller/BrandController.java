package com.startzhao.spzx.product.controller;

import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: Brand
 * Package: com.startzhao.spzx.product.controller
 *
 * @Author StartZhao
 * @Create 2024/6/4 23:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/findAll")
    public Result<List<Brand>> findAll() {
        List<Brand> list = brandService.list();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}