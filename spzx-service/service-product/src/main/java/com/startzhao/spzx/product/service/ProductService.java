package com.startzhao.spzx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.h5.ProductSkuDTO;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.model.vo.h5.ProductItemVO;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * ClassName: ProductService
 * Package: com.startzhao.spzx.product.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/4 23:29
 * @Version 1.0
 */
public interface ProductService extends IService<Product> {


    /**
     * 分页查询
     * @param page
     * @param limit
     * @param productDTO
     * @return
     */
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDTO productDTO);

    /**
     * 商品详情
     * @param skuId
     * @return
     */
    ProductItemVO item(Long skuId);
}
