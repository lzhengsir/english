package com.gcc.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: lzhen
 * @since: 2022/11/22 12:41
 * @description:
 */
@Data
@ApiModel(value = "AppUserLogin", description = "用户登录")
public class AppUserLogin implements Serializable {
    private static final long serialVersionUID = -474866300083522226L;

    @Pattern(regexp = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+", message = "用户名不能带有空白字符")
    @ApiModelProperty(value = "用户名或手机号")
    private String usernameOrPhone;

    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{6,18}$", message = "密码必须在6-18位之间(必须包含数字和字母)")
    @ApiModelProperty(value = "密码")
    private String pwd;
}
