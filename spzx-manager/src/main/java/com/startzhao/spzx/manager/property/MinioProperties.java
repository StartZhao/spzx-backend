package com.startzhao.spzx.manager.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ClassName: MinioProperties
 * Package: com.startzhao.spzx.manager.property
 * Description: Minio配置类
 *
 * @Author StartZhao
 * @Create 2024/5/26 21:21
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {

    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;

}
