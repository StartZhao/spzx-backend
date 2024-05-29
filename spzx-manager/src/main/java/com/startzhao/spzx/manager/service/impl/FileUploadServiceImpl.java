package com.startzhao.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.startzhao.spzx.common.exception.StartZhaoException;
import com.startzhao.spzx.manager.property.MinioProperties;
import com.startzhao.spzx.manager.service.FileUploadService;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

/**
 * ClassName: FileUploadServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 文件上传具体实现
 *
 * @Author StartZhao
 * @Create 2024/5/26 23:38
 * @Version 1.0
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file) {

        try {
            // 创建 minio客户端对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpointUrl())
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                            .build();

            // 判断桶是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // 如果不存在就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                log.info("Bucket already exists.");
            }
            // 文件名
            String fileName = DateUtil.format(new Date(), "yyyyMMdd") + "/" + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
            // 上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .build());
            String fileUrl = minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;
            log.info("FileUploadServiceImpl.fileUpload业务结束，结果{}", "头像上传成功");
            return fileUrl;
        } catch (Exception e) {
            throw new StartZhaoException(ResultCodeEnum.SYSTEM_ERROR);
        }


    }

}
