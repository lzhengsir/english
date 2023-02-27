package com.gcc.controller;


import com.gcc.common.Constants;
import com.gcc.common.Result;
import com.gcc.entity.vo.SystemUserVo;
import com.gcc.service.SystemUserService;
import com.gcc.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 系统管理员 前端控制器
 * </p>
 *
 * @author lzheng
 * @since 2022-11-22
 */
@Api(tags = "管理员-管理员管理")
@RestController
@RequestMapping("/administer")
public class SystemUserController {

    @Resource
    private SystemUserService systemUserService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody @Valid SystemUserVo systemUserVo) {
        String token = systemUserService.login(systemUserVo);
        return Result.ok().data("token", token);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        systemUserService.logout(JwtUtils.getKeyByJwtToken(request, Constants.SESSION_IDP_KEY_IN_JWT));
        return Result.ok();
    }
}
