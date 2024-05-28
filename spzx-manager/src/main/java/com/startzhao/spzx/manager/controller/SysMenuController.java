package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.SysMenuService;
import com.startzhao.spzx.model.dto.system.AssginRoleDTO;
import com.startzhao.spzx.model.dto.system.SysUserDTO;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SysMenuController
 * Package: com.startzhao.spzx.manager.controller
 * Description: 系统用户相关接口
 *
 * @Author StartZhao
 * @Create 2024/5/23 18:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        return sysMenuService.findNodes();
    }

    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        return sysMenuService.saveSysUser(sysUser);
    }

    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        return sysMenuService.updateSysUser(sysUser);
    }

    @DeleteMapping("/deleteSysUserById/{userId}")
    public Result deleteSysUserById(@PathVariable Long userId) {
        return sysMenuService.deleteSysUserById(userId);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDTO assginRoleDTO) {
        return sysMenuService.doAssign(assginRoleDTO);
    }


}
