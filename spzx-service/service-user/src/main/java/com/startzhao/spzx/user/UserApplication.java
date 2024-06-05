package com.startzhao.spzx.user;

import com.startzhao.spzx.common.annotation.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: UserApplication
 * Package: com.startzhao.spzx.user
 * Description: 用户启动类
 *
 * @Author StartZhao
 * @Create 2024/6/5 10:16
 * @Version 1.0
 */
@SpringBootApplication
@EnableUserWebMvcConfiguration
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
