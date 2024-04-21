package com.startzhao.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.startzhao.spzx.manager.service.ValidateCodeService;
import com.startzhao.spzx.model.vo.system.ValidateCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ValidateCodeServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 验证业务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/4/21 16:06
 * @Version 1.0
 */
@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成验证码
     * 1、通过工具生成图片验证码
     * 2、将验证码存储到 redis 中
     * 3、返回 ValidateCodeVO 对象
     *
     * @return
     */
    @Override
    public ValidateCodeVO generateValidateCode() {

        // 使用 hutool 工具包生成图片验证码
        // 参数： 宽 高 验证码位数 干扰线数量
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String codeValue = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();

        String codeKey = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:validateCode:" + codeKey,codeValue,5, TimeUnit.MINUTES);

        ValidateCodeVO validateCodeVO = new ValidateCodeVO();
        validateCodeVO.setCodeKey(codeKey);
        validateCodeVO.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVO;
    }
}
