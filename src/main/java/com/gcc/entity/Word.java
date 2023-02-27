package com.gcc.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 单词
 * </p>
 *
 * @author lzheng
 * @since 2022-11-19
 */
@Data
@ApiModel(value = "Word对象", description = "单词")
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "单词拼写")
    private String spell;

    @ApiModelProperty(value = "重点意思")
    private String emphasisMean;

    @ApiModelProperty("音标")
    private String phonetic;

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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word = (Word) o;
        return Objects.equals(spell, word.spell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spell);
    }
}
