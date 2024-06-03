package com.startzhao.spzx.common.log.service.impl;

import com.startzhao.spzx.common.log.mapper.SysOperLogMapper;
import com.startzhao.spzx.common.log.service.AsyncOperLogService;
import com.startzhao.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * ClassName: AsyncOperLogServiceImpl
 * Package: com.startzhao.spzx.common.log.service.impl
 * Description: 异步日志具体实现类
 *
 * @Author StartZhao
 * @Create 2024/6/3 11:27
 * @Version 1.0
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /**
     * 异步执行保存日志操作
     * @param sysOperLog
     */
    @Async
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
