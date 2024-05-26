package com.startzhao.spzx.manager.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * ClassName: UserProperties
 * Package: com.startzhao.spzx.manager.property
 * Description: 用户属性配置类
 *
 * @Author StartZhao
 * @Create 2024/4/21 20:02
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "spzx.auth")
@Data
public class UserProperties {

    private List<String> noAuthUrls;
}
