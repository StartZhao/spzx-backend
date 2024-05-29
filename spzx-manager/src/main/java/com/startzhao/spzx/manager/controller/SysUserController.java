package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.manager.service.SysUserService;
import com.startzhao.spzx.model.dto.system.AssginRoleDTO;
import com.startzhao.spzx.model.dto.system.SysUserDTO;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.PageResult;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: SysUserController
 * Package: com.startzhao.spzx.manager.controller
 * Description: 系统用户相关接口
 *
 * @Author StartZhao
 * @Create 2024/5/23 18:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageResult<SysUser>> findByPage(@RequestBody SysUserDTO sysUserDTO, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        PageResult<SysUser> pageResult = sysUserService.findByPage(sysUserDTO, pageNum, pageSize);
        return Result.build(pageResult, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteSysUserById/{userId}")
    public Result deleteSysUserById(@PathVariable Long userId) {
        sysUserService.deleteSysUserById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDTO assginRoleDTO) {
        sysUserService.doAssign(assginRoleDTO);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
