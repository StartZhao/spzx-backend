package com.startzhao.spzx.common.exception;


import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.startzhao.spzx.common.exception
 * Description: 全局异常处理
 *
 * @Author StartZhao
 * @Create 2024/4/19 21:15
 * @Version 1.0
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.FAIL);
    }

    @ExceptionHandler(StartZhaoException.class)
    public Result error(StartZhaoException e) {
        e.printStackTrace();
        return Result.build(null, e.getCode(), e.getMessage());
    }


}
