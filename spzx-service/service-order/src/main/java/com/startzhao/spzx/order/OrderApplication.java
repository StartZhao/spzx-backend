package com.startzhao.spzx.order;

import com.startzhao.spzx.common.annotation.EnableUserTokenFeignInterceptor;
import com.startzhao.spzx.common.annotation.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ClassName: OrderApplication
 * Package: com.startzhao.spzx.order
 * Description: 订单启动类
 *
 * @Author StartZhao
 * @Create 2024/6/5 23:22
 * @Version 1.0
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.startzhao.spzx.feign.cart",
        "com.startzhao.spzx.feign.product",
        "com.startzhao.spzx.feign.user"
})
@EnableUserTokenFeignInterceptor
@EnableUserWebMvcConfiguration
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }

}
