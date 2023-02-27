package com.gcc.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "WordAdd", description = "单词")
public class WordAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "[a-zA-Z]+", message = "请输入正确的单词")
    @ApiModelProperty(value = "单词拼写")
    @ExcelProperty("单词")
    private String spell;

    @NotBlank
    @ApiModelProperty(value = "重点意思")
    @ExcelProperty("意思")
    private String emphasisMean;

    @ExcelProperty("音标")
    @ApiModelProperty("音标")
    private String phonetic;
}
