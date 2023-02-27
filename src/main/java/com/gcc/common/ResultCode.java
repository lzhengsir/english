package com.gcc.common;

/**
 * @className: ResultCode
 * @author: lzheng
 * @date: 2022/8/6 17:16
 * @description:
 */
public interface ResultCode {
    /**
     * 成功
     */
    Integer SUCCESS = 200;
    /**
     * 错误
     */
    Integer ERROR = 400;
    /**
     * 错误
     */
    Integer AUTHORIZATION_ERROR = 401;
}
