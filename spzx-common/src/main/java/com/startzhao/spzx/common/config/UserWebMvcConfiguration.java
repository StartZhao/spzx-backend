package com.startzhao.spzx.common.config;

import com.startzhao.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: UserWebMvcConfiguration
 * Package: com.startzhao.spzx.common.config
 * Description: 注册拦截器
 *
 * @Author StartZhao
 * @Create 2024/6/5 17:24
 * @Version 1.0
 */
public class UserWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserLoginAuthInterceptor userLoginAuthInterceptor ;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .addPathPatterns("/api/**");
    }
}
