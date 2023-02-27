package com.gcc.exception;

import com.gcc.common.Result;
import com.gcc.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: GolobalExceptionHandler
 * @author: lzheng
 * @date: 2022/8/6 22:44
 * @description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error("******************发生了其他异常******************");
        e.printStackTrace();
        return Result.error().code(ResultCode.ERROR);
    }

    /**
     * 参数接收异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("******************发生了参数接收异常******************");
        e.printStackTrace();
        return handleBindingResult(e.getBindingResult());
    }

    /**
     * 找出不符合参数的条件
     *
     * @param result
     * @return
     */
    private Result handleBindingResult(BindingResult result) {
        List<String> reasons = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError allError : allErrors) {
                reasons.add(allError.getDefaultMessage());
            }
        }
        if (reasons.size() == 0) {
            return Result.error().message("参数接收异常");
        }
        return Result.error().message(reasons.toString());
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ProjectException.class)
    public Result projectException(ProjectException e) {
        log.error("******************发生了自定义异常******************");
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
