package com.startzhao.spzx.common.log.service;

import com.startzhao.spzx.model.entity.system.SysOperLog;

/**
 * ClassName: AsyncOperLogService
 * Package: com.startzhao.spzx.common.log.service
 * Description: 异步日志业务
 *
 * @Author StartZhao
 * @Create 2024/6/3 11:26
 * @Version 1.0
 */
public interface AsyncOperLogService {


    /**
     * 异步执行保存日志操作
     * @param sysOperLog
     */
    public void saveSysOperLog(SysOperLog sysOperLog) ;


}
