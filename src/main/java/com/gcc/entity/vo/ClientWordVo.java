package com.gcc.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: lzhen
 * @since: 2022/11/20 23:28
 * @description: 返回给前台展示
 */
@Data
@ApiModel(value = "WordVo", description = "返回给前端展示")
public class ClientWordVo implements Serializable {
    private static final long serialVersionUID = 4376423067117525533L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "单词拼写")
    private String spell;

    @ApiModelProperty(value = "重点意思")
    private String emphasisMean;

    @ApiModelProperty(value = "联想记忆")
    private String association;

    @ApiModelProperty(value = "1-未掌握 2-已掌握 null-未学习")
    private Integer sign;

    @ApiModelProperty("音标")
    private String phonetic;
}
