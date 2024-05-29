package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.SysMenuService;
import com.startzhao.spzx.model.dto.system.AssginRoleDTO;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.entity.system.SysMenu;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        List<SysMenu> sysMenus = sysMenuService.findNodes();
        return Result.build(sysMenus,ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/saveSysMenu")
    public Result saveSysMenu(@RequestBody SysMenu sysMenu) {
         sysMenuService.saveSysMenu(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateSysMenu(@RequestBody SysMenu sysMenu) {
         sysMenuService.updateSysMenu(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/removeById/{id}")
    public Result deleteSysMenuById(@PathVariable Long id) {
        sysMenuService.deleteSysMenuById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result<Map<String,List>> findSysRoleMenuByRoleId(@PathVariable Long roleId) {
        Map<String, List> map = sysMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map,ResultCodeEnum.SUCCESS);
    }


}
