package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.common.constant.DeletedStatusConstant;
import com.startzhao.spzx.manager.mapper.BrandMapper;
import com.startzhao.spzx.manager.mapper.CategoryMapper;
import com.startzhao.spzx.manager.mapper.ProductMapper;
import com.startzhao.spzx.manager.service.ProductDetailsService;
import com.startzhao.spzx.manager.service.ProductService;
import com.startzhao.spzx.manager.service.ProductSkuService;
import com.startzhao.spzx.model.dto.product.ProductDTO;
import com.startzhao.spzx.model.entity.product.*;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.vo.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: Product
 * Package: com.startzhao.spzx.manager.service.impl
 *
 * @Author StartZhao
 * @Create 2024/5/30 16:37
 * @Version 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductDetailsService productDetailsService;
    /**
     * 根据品牌、分类id进行分页显示
     *
     * @param page
     * @param limit
     * @param productDTO
     * @return
     */
    @Override
    public PageResult<Product> getProductPageList(Integer page, Integer limit, ProductDTO productDTO) {
        IPage<Product> productPage = new Page<>(page,limit);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Product::getIsDeleted, DeletedStatusConstant.NOT_DELETED);
        Long brandId = productDTO.getBrandId();
        Long category1Id = productDTO.getCategory1Id();
        Long category2Id = productDTO.getCategory2Id();
        Long category3Id = productDTO.getCategory3Id();
        if (brandId != null) {
            queryWrapper.lambda().eq(Product::getBrandId,brandId);
        }
        if (category1Id != null) {
            queryWrapper.lambda().eq(Product::getCategory1Id,category1Id);
        }
        if (category2Id != null) {
            queryWrapper.lambda().eq(Product::getCategory2Id,category2Id);
        }
        if (category3Id != null) {
            queryWrapper.lambda().eq(Product::getCategory3Id,category3Id);
        }
        productPage = productMapper.selectPage(productPage, queryWrapper);
        List<Product> productList = productPage.getRecords();
        productList.forEach(product -> {
            product.setBrandName(brandMapper.selectById(product.getBrandId()).getName());
            product.setCategory1Name(categoryMapper.selectById(product.getCategory1Id()).getName());
            product.setCategory2Name(categoryMapper.selectById(product.getCategory2Id()).getName());
            product.setCategory3Name(categoryMapper.selectById(product.getCategory3Id()).getName());
        });
        return PageResult.<Product>builder()
                .records(productList)
                .total(productPage.getTotal()).build();
    }

    /**
     * 进行商品添加，涉及多个表
     *
     * @param product 实体对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Product product) {
        productMapper.insert(product);
        Long productId = product.getId();
        String productName = product.getName();
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size();i++) {
            ProductSku productSku = productSkuList.get(i);
            productSku.setProductId(productId);
            productSku.setSkuCode(productId + "_" + i);
            productSku.setSkuName(productName + productSku.getSkuSpec());
            productSku.setStatus(0);
        }
        productSkuService.saveBatch(productSkuList);
            String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(productId);
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsService.save(productDetails);
        return true;
    }

    /**
     * 根据商品 id 获取商品
     *
     * @param id
     * @return
     */
    @Override
    public Product getById(Long id) {
        Product product = productMapper.selectById(id);

        QueryWrapper<ProductSku> productSkuQueryWrapper = new QueryWrapper<>();
        productSkuQueryWrapper.lambda().eq(ProductSku::getProductId,id);
        List<ProductSku> productSkuList = productSkuService.list(productSkuQueryWrapper);
        product.setProductSkuList(productSkuList);

        QueryWrapper<ProductDetails> productDetailsQueryWrapper = new QueryWrapper<>();
        productDetailsQueryWrapper.lambda().eq(ProductDetails::getProductId,id);
        ProductDetails productDetails = productDetailsService.getOne(productDetailsQueryWrapper);
        if (productDetails == null) {
            return  product;
        }
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);

        return product;
    }

    /**
     * 根据商品id 更新商品
     *
     * @param product 实体对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Product product) {
        productMapper.updateById(product);

        Long productId = product.getId();
        QueryWrapper<ProductSku> productSkuQueryWrapper = new QueryWrapper<>();
        productSkuQueryWrapper.lambda().eq(ProductSku::getProductId,productId);
        productSkuService.remove(productSkuQueryWrapper);
        String productName = product.getName();
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size();i++) {
            ProductSku productSku = productSkuList.get(i);
            productSku.setProductId(productId);
            productSku.setSkuCode(productId + "_" + i);
            productSku.setSkuName(productName + productSku.getSkuSpec());
            productSku.setStatus(0);
        }
        productSkuService.saveBatch(productSkuList);

        String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(productId);
        productDetails.setImageUrls(detailsImageUrls);
        UpdateWrapper<ProductDetails> productDetailsUpdateWrapper = new UpdateWrapper<>();
        productDetailsService.update(productDetails,productDetailsUpdateWrapper);
        return true;
    }

    /**
     * 根据商品id删除商品
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Long id) {
        productMapper.deleteById(id);

        QueryWrapper<ProductSku> productSkuQueryWrapper = new QueryWrapper<>();
        productSkuQueryWrapper.lambda().eq(ProductSku::getProductId,id);
        productSkuService.remove(productSkuQueryWrapper);

        QueryWrapper<ProductDetails> productDetailsQueryWrapper = new QueryWrapper<>();
        productDetailsQueryWrapper.lambda().eq(ProductDetails::getProductId,id);
        productDetailsService.remove(productDetailsQueryWrapper);
        return true;
    }

    /**
     * 审核商品是否上架
     *
     * @param id
     * @param auditStatus
     */
    @Override
    public void updateAuditStatus(Long id, Long auditStatus) {
        UpdateWrapper<Product> productUpdateWrapper = new UpdateWrapper<>();
        String auditMessage;
        if (auditStatus == 1) {
            auditMessage = "审核通过";
        } else {
            auditMessage = "审核不通过";
        }
        productUpdateWrapper.lambda().eq(Product::getId,id).set(Product::getAuditStatus,auditStatus).set(Product::getAuditMessage,auditMessage);
        productMapper.update(productUpdateWrapper);
    }

    /**
     * 上下架
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Long status) {
        UpdateWrapper<Product> productUpdateWrapper = new UpdateWrapper<>();
        productUpdateWrapper.lambda().eq(Product::getId,id).set(Product::getStatus,status);
        productMapper.update(productUpdateWrapper);
    }
}