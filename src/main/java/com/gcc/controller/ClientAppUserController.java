package com.gcc.controller;


import com.gcc.common.Constants;
import com.gcc.common.Result;
import com.gcc.entity.vo.AppUserLogin;
import com.gcc.entity.vo.AppUserRegister;
import com.gcc.entity.vo.AppUserVo;
import com.gcc.service.AppUserService;
import com.gcc.service.SmsService;
import com.gcc.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/client")
@Api(tags = "客户端-用户管理")
public class ClientAppUserController {
    @Resource
    private SmsService smsService;
    @Resource
    private AppUserService appUserService;

    @ApiOperation("发送注册短信验证码")
    @GetMapping("/registersms/{phone}")
    public Result sendMessage(@PathVariable String phone) {
        boolean flag = smsService.sendShortMessage(phone);
        return flag ? Result.ok().message("验证码分送成功，十五分钟内有效") : Result.error().message("短信发送失败，请稍后再试");
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody AppUserRegister appUserRegister) {
        appUserService.register(appUserRegister);
        return Result.ok().message("注册成功");
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody AppUserLogin appUserLogin) {
        String token = appUserService.login(appUserLogin);
        return Result.ok().message("登陆成功").data("token", token);
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/user")
    public Result getUserInfo() {
        AppUserVo userInfo = appUserService.getUserInfo();
        return Result.ok().data("item", userInfo);
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        appUserService.logout(JwtUtils.getKeyByJwtToken(request, Constants.SESSION_IDP_KEY_IN_JWT));
        return Result.ok();
    }
}
