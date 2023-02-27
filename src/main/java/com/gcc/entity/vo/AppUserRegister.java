package com.gcc.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: lzhen
 * @since: 2022/11/22 12:41
 * @description:
 */
@Data
@ApiModel(value = "AppUserRegister", description = "用户注册")
public class AppUserRegister implements Serializable {
    private static final long serialVersionUID = -474866300083522226L;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @Pattern(regexp = "0?(13|14|15|18)[0-9]{9}", message = "请输入正确的11位手机号")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @Pattern(regexp = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+", message = "用户名不能带有空白字符")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,18}$", message = "密码必须在6-18位之间(必须包含数字和字母)")
    @ApiModelProperty(value = "密码")
    private String pwd;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,18}$", message = "密码必须在6-18位之间(必须包含数字和字母)")
    @ApiModelProperty(value = "确认密码")
    private String surePwd;
}
