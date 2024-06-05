package com.startzhao.spzx.user.service.impl;

import com.alibaba.nacos.common.http.HttpUtils;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.common.utils.HttpUtil;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.user.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: Sms
 * Package: com.startzhao.spzx.user.service.impl
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:19
 * @Version 1.0
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @Override
    public String sendCode(String phone) {
        String code = redisTemplate.opsForValue().get("phone:code:" + phone);
        if (!StringUtils.isEmpty(code)) {
            return code;
        }
        String validateCode = RandomStringUtils.randomNumeric(4);
        redisTemplate.opsForValue().set("phone:code:" + phone, validateCode, 5, TimeUnit.MINUTES);
        sendSms(phone, validateCode);
        return validateCode;
    }

    private void sendSms(String phone, String validateCode) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "938a8491cea74e43a48edf0a67070051";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:" + validateCode);
        bodys.put("template_id", "CST_ptdie100");
        bodys.put("phone_number", phone);

        try {
            /**
             * 重要提示如下:
             * HttpUtil请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new StartZhaoException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}