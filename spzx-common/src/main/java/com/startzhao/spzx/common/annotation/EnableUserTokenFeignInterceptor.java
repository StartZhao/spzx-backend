package com.startzhao.spzx.common.annotation;

import com.startzhao.spzx.common.feign.UserTokenFeignInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: EnableUserTokenFeignInterceptor
 * Package: com.startzhao.spzx.common.annotation
 * Description: 启用feign拦截器
 *
 * @Author StartZhao
 * @Create 2024/6/6 0:09
 * @Version 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = UserTokenFeignInterceptor.class)
public @interface EnableUserTokenFeignInterceptor {

}
