package com.startzhao.spzx.manager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.startzhao.spzx.manager.mapper.SysRoleMenuMapper;
import com.startzhao.spzx.manager.service.SysRoleMenuService;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.entity.system.SysRoleMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: SysRoleMenuServiceImpl
 * Package: com.startzhao.spzx.manager.service.impl
 * Description: 角色菜单关系相关业务具体实现类
 *
 * @Author StartZhao
 * @Create 2024/5/28 19:57
 * @Version 1.0
 */
@Service
@Slf4j
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
}
