package com.startzhao.spzx.manager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileUploadService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/5/26 23:37
 * @Version 1.0
 */
public interface FileUploadService {

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    String fileUpload(MultipartFile file);
}
