package com.startzhao.spzx.manager.config;


import com.startzhao.spzx.manager.interceptor.LoginAuthInterceptor;
import com.startzhao.spzx.manager.property.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: WebMvcConfiguration
 * Package: com.startzhao.spzx.manager.config
 * Description: 配置类
 *
 * @Author StartZhao
 * @Create 2024/4/19 22:31
 * @Version 1.0
 */
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    LoginAuthInterceptor loginAuthInterceptor;
    @Autowired
    UserProperties userProperties;

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns(userProperties.getNoAuthUrls())
                .addPathPatterns("/**");
    }

    // 跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");                // 允许所有的请求头
    }

}
