package com.startzhao.spzx.common.log.aspect;

import com.startzhao.spzx.common.log.annotation.Log;
import com.startzhao.spzx.common.log.service.AsyncOperLogService;
import com.startzhao.spzx.common.log.utils.LogUtil;
import com.startzhao.spzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: LogAspect
 * Package: com.startzhao.spzx.common.log.aspect
 * Description: 日志切面类
 *
 * @Author StartZhao
 * @Create 2024/5/31 1:04
 * @Version 1.0
 */
@Slf4j
@Component
@Aspect
public class LogAspect {

    @Autowired
    private AsyncOperLogService asyncOperLogService;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , Log sysLog) {
        // 构建前置参数
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        String title = sysLog.title();
        log.info("LogAspect...doAroundAdvice方法执行了"+title);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();              // 执行业务方法
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 0 , null) ;;
        } catch (Throwable e) {
            // 代码执行进入到catch中，业务方法执行产生异常
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 1 , e.getMessage()) ;
            throw new RuntimeException(e);
        }
        // 保存日志数据
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed ;                                // 返回执行结果
    }
}
