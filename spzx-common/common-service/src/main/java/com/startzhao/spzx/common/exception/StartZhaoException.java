package com.startzhao.spzx.common.exception;

import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * ClassName: StartZhaoException
 * Package: com.startzhao.spzx.common.exception
 * Description: 自定义异常
 *
 * @Author StartZhao
 * @Create 2024/4/19 21:25
 * @Version 1.0
 */
@Data
public class StartZhaoException extends RuntimeException {

    // 错误状态码
    private Integer code;
    // 错误消息
    private String message;

    // 返回结果枚举类
    private ResultCodeEnum resultCodeEnum;

    public StartZhaoException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public StartZhaoException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
