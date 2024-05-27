package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.SysRoleUserMapper;
import com.startzhao.spzx.manager.service.SysRoleUserService;
import com.startzhao.spzx.model.entity.system.SysRoleUser;
import com.startzhao.spzx.model.entity.system.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClassName: SysRoleUserServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 角色用户关联相关业务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/5/27 16:32
 * @Version 1.0
 */
@Service
@Slf4j
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

}
