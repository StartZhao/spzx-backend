package com.startzhao.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.xmlbeans.impl.jam.xml.TunnelledException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: LoginAuthInterceptor
 * Package: com.startzhao.spzx.manager.interceptor
 * Description: 校验登录拦截器
 *
 * @Author StartZhao
 * @Create 2024/4/21 19:39
 * @Version 1.0
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、获取请求方式，如果是预检请求放行
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        // 2、获取 token
        String token = request.getHeader("token");
        // 3、如果 token 为空，则返回错误提示
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }
        // 4、如果 token 不为空，查询 redis
        String sysUserInfoJSON = redisTemplate.opsForValue().get("user:login:" + token);
        // 5、如果 redis 查不到相应的数据，返回错误提示
        if (StrUtil.isEmpty(sysUserInfoJSON)) {
            responseNoLoginInfo(response);
            return false;
        }
        // 6、redis 中查到数据，将其数据放入 ThreadLocal
        AuthContextUtil.set(JSON.parseObject(sysUserInfoJSON, SysUser.class));
        // 7、更新 redis 的数据
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);
        // 8、放行
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 删除 ThreadLocal 数据
        AuthContextUtil.remove();
    }
}
