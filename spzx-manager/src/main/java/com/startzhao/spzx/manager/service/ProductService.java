package com.startzhao.spzx.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.dto.product.ProductDTO;
import com.startzhao.spzx.model.entity.product.Product;
import com.startzhao.spzx.model.vo.common.PageResult;

/**
 * ClassName: ProductService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/30 16:36
 * @Version 1.0
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据品牌、分类id进行分页显示
     * @param page
     * @param limit
     * @param productDTO
     * @return
     */
    PageResult<Product> getProductPageList(Integer page, Integer limit, ProductDTO productDTO);

    /**
     * 进行商品添加，涉及多个表
     * @param product 实体对象
     * @return
     */
    @Override
    boolean save(Product product);

    /**
     * 根据商品 id 获取商品
     * @param id
     * @return
     */
    Product getById(Long id);

    /**
     * 根据商品id 更新商品
     * @param product 实体对象
     * @return
     */
    @Override
    boolean updateById(Product product);

    /**
     * 根据商品id删除商品
     * @param id
     * @return
     */
    boolean removeById(Long id);

    /**
     * 审核商品是否上架
     * @param id
     * @param auditStatus
     */
    void updateAuditStatus(Long id, Long auditStatus);

    /**
     * 上下架
     * @param id
     * @param status
     */
    void updateStatus(Long id, Long status);
}
