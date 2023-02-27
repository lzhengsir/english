package com.gcc.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppUser对象", description = "前台用户")
public class AppUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户名")
    private String username;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
