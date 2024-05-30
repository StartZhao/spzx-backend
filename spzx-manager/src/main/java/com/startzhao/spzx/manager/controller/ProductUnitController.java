package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.ProductUnitService;
import com.startzhao.spzx.model.entity.base.ProductUnit;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: ProductUnit
 * Package: com.startzhao.spzx.manager.controller
 *
 * @Author StartZhao
 * @Create 2024/5/30 17:02
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {

    @Autowired
    private ProductUnitService productUnitService;

    @GetMapping("/findAll")
    public Result<List<ProductUnit>> findAll() {
        List<ProductUnit> list = productUnitService.list();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}