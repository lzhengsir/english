package com.gcc.exception;

import com.gcc.common.ExceptionEnum;
import com.gcc.common.ResultCode;
import lombok.Getter;

/**
 * @author: lzhen
 * @since: 2022/11/1 17:37
 * @description: 自定义异常
 */
@Getter
public class ProjectException extends RuntimeException {
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 信息
     */
    private final String msg;

    public ProjectException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public ProjectException(String msg) {
        this.code = ResultCode.ERROR;
        this.msg = msg;
    }
}
