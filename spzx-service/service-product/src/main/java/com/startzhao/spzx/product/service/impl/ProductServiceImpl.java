package com.startzhao.spzx.product.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.model.dto.h5.ProductSkuDTO;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.entity.product.ProductDetails;
import com.startzhao.spzx.model.entity.product.ProductSku;
import com.startzhao.spzx.model.vo.h5.ProductItemVO;
import com.startzhao.spzx.product.mapper.ProductDetailsMapper;
import com.startzhao.spzx.product.mapper.ProductMapper;
import com.startzhao.spzx.product.mapper.ProductSkuMapper;
import com.startzhao.spzx.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: Product
 * Package: com.startzhao.spzx.product.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/4 23:30
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;



    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @param productDTO
     * @return
     */
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDTO productDTO) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productDTO);
        return new PageInfo<>(productSkuList);
    }

    /**
     * 商品详情
     *
     * @param skuId
     * @return
     */
    @Override
    public ProductItemVO item(Long skuId) {
        ProductSku productSku = productSkuMapper.selectById(skuId);

        Product product = productMapper.selectById(productSku.getProductId());
        QueryWrapper<ProductSku> productSkuQueryWrapper = new QueryWrapper<>();
        productSkuQueryWrapper.lambda()
                        .eq(ProductSku::getProductId,product.getId());
        List<ProductSku> productSkuList = productSkuMapper.selectList(productSkuQueryWrapper);
        product.setProductSkuList(productSkuList);
        QueryWrapper<ProductDetails> productDetailsQueryWrapper = new QueryWrapper<>();
        productDetailsQueryWrapper.lambda()
                .eq(ProductDetails::getProductId,product.getId());
        ProductDetails productDetails = productDetailsMapper.selectOne(productDetailsQueryWrapper);

        String imageUrls = null;
        List<String> detailsImageUrlList = null;
        if (productDetails != null) {
            imageUrls = productDetails.getImageUrls();
            detailsImageUrlList = Arrays.asList(imageUrls.split(","));
        }
        product.setDetailsImageUrls(imageUrls);

        Map<String, Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(),item.getId());
        });



        ProductItemVO productItemVO = new ProductItemVO();
        productItemVO.setProductSku(productSku);
        productItemVO.setProduct(product);
        productItemVO.setDetailsImageUrlList(detailsImageUrlList);
        productItemVO.setSkuSpecValueMap(skuSpecValueMap);
        productItemVO.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVO.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        return productItemVO;
    }
}