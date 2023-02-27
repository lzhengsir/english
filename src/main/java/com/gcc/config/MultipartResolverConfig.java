package com.gcc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author: lzhen
 * @since: 2022/11/10 14:06
 * @description: 文件上传配置
 */
@Configuration
public class MultipartResolverConfig {
    /**
     * 文件上传解析组件 id必须为multipartResolver springmvc默认使用该id找该组件
     *
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        //总大小限制
        multipartResolver.setMaxUploadSize(1073741824);
        //单文件大小限制
        multipartResolver.setMaxUploadSizePerFile(1073741824);
        return multipartResolver;
    }
}
