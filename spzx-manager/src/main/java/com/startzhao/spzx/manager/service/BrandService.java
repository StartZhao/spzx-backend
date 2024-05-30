package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.entity.product.Brand;
import com.startzhao.spzx.model.vo.common.PageResult;

/**
 * ClassName: BrandService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/30 8:21
 * @Version 1.0
 */
public interface BrandService extends IService<Brand> {

    /**
     * 分页显示
     * @param page
     * @param limit
     * @return
     */
    PageResult<Brand> getBrandPageList(Integer page, Integer limit);

}
