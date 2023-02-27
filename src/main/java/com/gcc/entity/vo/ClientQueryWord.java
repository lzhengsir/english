package com.gcc.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 单词
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
@Data
@ApiModel(value = "ClientQueryWord", description = "前台用户查询单词的条件")
public class ClientQueryWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "单词拼写")
    private String spell;

    @ApiModelProperty(value = "重点意思")
    private String emphasisMean;
}
