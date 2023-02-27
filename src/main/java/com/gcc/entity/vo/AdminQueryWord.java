package com.gcc.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 单词
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
@Data
@ApiModel(value = "AdminQueryWord", description = "管理员查询单词的条件")
public class AdminQueryWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词或意思")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
