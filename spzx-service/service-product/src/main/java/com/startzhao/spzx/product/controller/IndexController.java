package com.startzhao.spzx.product.controller;

import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.h5.IndexVO;
import com.startzhao.spzx.product.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: Index
 * Package: com.startzhao.spzx.product.controller
 *
 * @Author StartZhao
 * @Create 2024/6/4 16:33
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/product/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping
    public Result<IndexVO> findData() {
        IndexVO indexVO = indexService.findData();
        return Result.build(indexVO, ResultCodeEnum.SUCCESS);
    }


}