package com.gcc.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 单词标记
 * </p>
 *
 * @author lzheng
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "WordTagVo", description = "单词标记")
public class WordTagVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "单词id不能未空")
    @ApiModelProperty(value = "单词id")
    private String wordId;

    @Max(value = 2, message = "掌握情况最大为2")
    @Min(value = 1, message = "掌握情况最小为1")
    @ApiModelProperty(value = "1-未掌握 2-已掌握")
    private Integer sign;

    @ApiModelProperty("联想")
    private String association;
}
