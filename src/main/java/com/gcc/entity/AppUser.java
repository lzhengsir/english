package com.gcc.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "加密的密码")
    private String pwd;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "1-已删除 0-未删除")
    private Integer deleted;

    @Version
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "乐观锁版本控制")
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
