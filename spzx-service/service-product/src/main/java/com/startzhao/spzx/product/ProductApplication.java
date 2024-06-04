package com.startzhao.spzx.product;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: ProductApplication
 * Package: com.startzhao.spzx.product
 * Description: 启动类
 *
 * @Author StartZhao
 * @Create 2024/6/4 9:06
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(value = "com.startzhao.spzx.product.mapper")
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
