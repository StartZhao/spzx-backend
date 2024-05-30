package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.manager.mapper.ProductSpecMapper;
import com.startzhao.spzx.model.entity.product.ProductSpec;
import com.startzhao.spzx.model.vo.common.PageResult;

/**
 * ClassName: ProductSpecService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/30 16:03
 * @Version 1.0
 */
public interface ProductSpecService extends IService<ProductSpec> {

    /**
     * 分页显示
     * @param page
     * @param limit
     * @return
     */
    PageResult<ProductSpec> getProductSpecPageList(Integer page, Integer limit);
}
