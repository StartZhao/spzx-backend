package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.common.utils.AuthContextUtil;
import com.startzhao.spzx.manager.service.SysMenuService;
import com.startzhao.spzx.manager.service.ValidateCodeService;
import com.startzhao.spzx.model.entity.system.SysUser;
import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.system.LoginVO;
import com.startzhao.spzx.manager.service.SysUserService;
import com.startzhao.spzx.model.dto.system.LoginDTO;
import com.startzhao.spzx.model.vo.system.SysMenuVO;
import com.startzhao.spzx.model.vo.system.ValidateCodeVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * ClassName: IndexController
 * Package: com.startzhao.controller
 * Description: 首页相关接口
 *
 * @Author StartZhao
 * @Create 2024/4/19 16:54
 * @Version 1.0
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token")String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo() {
        SysUser sysUser = AuthContextUtil.get();
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = sysUserService.login(loginDTO);
        return Result.build(loginVO, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVO> generateValidateCode() {
        ValidateCodeVO validateCodeVO = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVO, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/menus")
    public Result<List<SysMenuVO>> menus() {
        List<SysMenuVO> menuList = sysMenuService.findUserMenuList();
        return Result.build(menuList,ResultCodeEnum.SUCCESS);
    }
}
