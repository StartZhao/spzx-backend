package com.startzhao.spzx.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.startzhao.spzx.model.vo.h5.IndexVO;

/**
 * ClassName: IndexService
 * Package: com.startzhao.spzx.product.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/6/4 16:34
 * @Version 1.0
 */
public interface IndexService {

    /**
     * 组装 IndexVO属性
     * @return
     */
    IndexVO findData();
}
