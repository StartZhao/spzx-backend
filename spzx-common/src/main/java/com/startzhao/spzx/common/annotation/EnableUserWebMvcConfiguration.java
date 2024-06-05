package com.startzhao.spzx.common.annotation;

import com.startzhao.spzx.common.config.UserWebMvcConfiguration;
import com.startzhao.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: EnableUserWebMvcConfiguration
 * Package: com.startzhao.spzx.common.annotation
 * Description: 使用拦截器配置类注解
 *
 * @Author StartZhao
 * @Create 2024/6/5 17:25
 * @Version 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = { UserLoginAuthInterceptor.class , UserWebMvcConfiguration.class})
public @interface EnableUserWebMvcConfiguration {

}
