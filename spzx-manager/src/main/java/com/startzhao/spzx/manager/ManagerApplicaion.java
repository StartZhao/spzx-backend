package com.startzhao.spzx.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: ManagerApplicaion
 * Package: com.startzhao
 * Description: 后台管理启动类
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:50
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.startzhao.spzx.manager.mapper")
@ComponentScan(basePackages = {"com.startzhao.spzx.common", "com.startzhao.spzx.manager"})
public class ManagerApplicaion {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplicaion.class, args);
    }
}
