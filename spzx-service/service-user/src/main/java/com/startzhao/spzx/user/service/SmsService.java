package com.startzhao.spzx.user.service;

/**
 * ClassName: SmsService
 * Package: com.startzhao.spzx.user.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:18
 * @Version 1.0
 */
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    String sendCode(String phone);
}
