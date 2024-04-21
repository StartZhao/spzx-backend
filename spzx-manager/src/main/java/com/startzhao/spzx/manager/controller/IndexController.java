package com.startzhao.spzx.manager.controller;

import com.startzhao.spzx.model.vo.common.Result;
import com.startzhao.spzx.model.vo.common.ResultCodeEnum;
import com.startzhao.spzx.model.vo.system.LoginVO;
import com.startzhao.spzx.manager.service.SysUserService;
import com.startzhao.spzx.model.dto.system.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = sysUserService.login(loginDTO);
        return Result.build(loginVO, ResultCodeEnum.SUCCESS);
    }
}
