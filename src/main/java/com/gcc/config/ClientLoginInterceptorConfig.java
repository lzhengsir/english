package com.gcc.config;

import com.gcc.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author: lzhen
 * @since: 2022/11/21 22:51
 * @description:
 */
@SpringBootConfiguration
public class ClientLoginInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(redisTemplate))
                .addPathPatterns("/client/**")
                .excludePathPatterns("/client/login")
                .excludePathPatterns("/client/register")
                .excludePathPatterns("/client/registersms/**");
    }
}
