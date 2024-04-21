package com.startzhao.spzx.manager.service;

import com.startzhao.spzx.model.vo.system.ValidateCodeVO;

/**
 * ClassName: ValidateCodeService
 * Package: com.startzhao.spzx.manager.service
 * Description:
 *
 * @Author StartZhao
 * @Create 2024/4/21 16:05
 * @Version 1.0
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     * @return
     */
    ValidateCodeVO generateValidateCode();

}
