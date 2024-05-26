package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.FileUploadService;
import com.startzhao.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: FileUploadController
 * Package: com.startzhao.spzx.manager.controller
 * Description: 文件上传接口
 *
 * @Author StartZhao
 * @Create 2024/5/26 23:33
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam(value = "file")MultipartFile file) {
        return fileUploadService.fileUpload(file);
    }

}
