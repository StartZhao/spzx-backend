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
        return sysUserService.saveSysUser(sysUser);
    }

    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateSysUser(sysUser);
    }

    @DeleteMapping("/deleteSysUserById/{userId}")
    public Result deleteSysUserById(@PathVariable Long userId) {
        return sysUserService.deleteSysUserById(userId);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDTO assginRoleDTO) {
        return sysUserService.doAssign(assginRoleDTO);
    }


}
