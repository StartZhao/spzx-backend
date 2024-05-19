package com.startzhao.spzx.manager;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.startzhao.spzx.manager.property.UserProperty;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@EnableConfigurationProperties({UserProperty.class})
public class ManagerApplicaion {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplicaion.class, args);
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
