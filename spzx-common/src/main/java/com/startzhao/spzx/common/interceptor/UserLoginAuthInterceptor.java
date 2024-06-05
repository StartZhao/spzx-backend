package com.startzhao.spzx.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.model.entity.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName: UserLoginAuthInterceptor
 * Package: com.startzhao.spzx.common.interceptor
 * Description: 拦截所有以api开头的接口
 *
 * @Author StartZhao
 * @Create 2024/6/5 17:22
 * @Version 1.0
 */
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果token不为空，那么此时验证token的合法性
        String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + request.getHeader("token"));
        AuthContextUtil.setUserInfo(JSON.parseObject(userInfoJSON , UserInfo.class));
        return true ;

    }

}
