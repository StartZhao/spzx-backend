package com.startzhao.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.startzhao.spzx.manager.service.SysRoleService;
import com.startzhao.spzx.model.dto.system.AssginMenuDTO;
import com.startzhao.spzx.model.dto.system.SysRoleDTO;
import com.startzhao.spzx.model.entity.system.SysRole;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.apache.ibatis.annotations.Param;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SysRoleController
 * Package: com.startzhao.spzx.manager.controller
 * Description: 系统角色相关接口
 *
 * @Author StartZhao
 * @Create 2024/4/23 17:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageResult<SysRole>> findByPage(@PathVariable(value = "pageNum") Integer pageNum, @PathVariable(value = "pageSize") Integer pageSize, @RequestBody SysRoleDTO sysRoleDTO) {
        PageResult<SysRole> pageResult = sysRoleService.findByPage(sysRoleDTO, pageNum, pageSize);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
        return sysRoleService.saveSysRole(sysRole);
    }

    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        return sysRoleService.updateSysRole(sysRole);
    }


    @DeleteMapping("/deleteSysRole/{roleId}")
    public Result deleteSysRole(@PathVariable(value = "roleId") Long roleId) {
        return sysRoleService.deleteSysRole(roleId);
    }

    @GetMapping("/findAllRoles/{userId}")
    public Result<Map<String,List>> findAllRoles(@PathVariable(value = "userId") Long userId) {
        return sysRoleService.findAllRoles(userId);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDTO assignMenuDTO) {
        return sysRoleService.doAssign(assignMenuDTO);
    }
}
