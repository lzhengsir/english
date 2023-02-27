package com.gcc.interceptor;

import com.gcc.common.Constants;
import com.gcc.common.ExceptionEnum;
import com.gcc.common.ThreadLocalHolder;
import com.gcc.exception.ProjectException;
import com.gcc.util.JwtUtils;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: lzhen
 * @since: 2022/11/21 13:40
 * @description:
 */
@Data
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (Constants.OPTIONS.equalsIgnoreCase(request.getMethod())) {
            //如果是预检请求则不拦截
            return true;
        }
        //如果jwt有效
        if (!JwtUtils.checkToken(request)) {
            throw new ProjectException(ExceptionEnum.PLEASE_LOGIN);
        }
        //获取用户id
        String sessionId = JwtUtils.getKeyByJwtToken(request, Constants.SESSION_IDP_KEY_IN_JWT);
        //往redis中查找sessionId
        String userid = redisTemplate.boundValueOps(sessionId).get();
        //如果查不到，则说明用户未登录
        if (userid == null) {
            throw new ProjectException(ExceptionEnum.PLEASE_LOGIN);
        }
        //设置用户id
        ThreadLocalHolder.USERID.set(userid);
        return true;
    }
}
