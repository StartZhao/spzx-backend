package com.startzhao.spzx.feign.product;

import com.startzhao.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName: ProductFeignClient
 * Package: com.startzhao.spzx.feign.product
 * Description: 商品服务远程调用接口
 *
 * @Author StartZhao
 * @Create 2024/6/5 18:39
 * @Version 1.0
 */
@FeignClient(value = "service-product")
public interface ProductFeignClient {

    @GetMapping("/api/product/getBySkuId/{skuId}")
    ProductSku getBySkuId(@PathVariable Long skuId) ;

}
