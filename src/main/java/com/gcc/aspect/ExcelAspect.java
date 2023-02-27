package com.gcc.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcc.common.Constants;
import com.gcc.common.ExceptionEnum;
import com.gcc.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: lzhen
 * @since: 2022/11/10 12:11
 * @description:
 */
@Aspect
@Component
@Slf4j
public class ExcelAspect {
    @Pointcut("execution(* com..controller.*.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object webLog(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        //收到请求,记录请求内容
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
        log.info("请求地址: " + request.getRequestURL().toString());
        log.info("请求方法的类型: " + request.getMethod());
        log.info("远端的IP地址: " + request.getRemoteAddr());
        log.info("控制器中的方法: " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        log.info("参数: " + Arrays.toString(args));
        try {
            Object result = pjp.proceed(args);
            //请求处理完成, 返回内容
            log.info("服务器响应的信息: " + new ObjectMapper().writeValueAsString(result));
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Pointcut("execution(* com..controller.*.uploadExcel*(..))")
    public void checkExcelType() {
    }

    @Before("checkExcelType()")
    public void checkExcelType(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        if (arg instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) arg;
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            //如果文件类型不匹配
            String point = ".";
            if (!Constants.EXCEL_SUFFIX_SET.contains(originalFilename.substring(originalFilename.lastIndexOf(point)))) {
                throw new ProjectException(ExceptionEnum.PLEASE_UPLOAD_EXCEL);
            }
        }
    }
}
