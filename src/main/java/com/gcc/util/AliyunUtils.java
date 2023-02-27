package com.gcc.util;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: lzhen
 * @since: 2022/12/1 16:46
 * @description:
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Setter
public class AliyunUtils implements InitializingBean {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String wordBookExcelTemplate;

    public static String ENDPOINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String WORD_BOOK_EXCEL_TEMPLATE;

    @Override
    public void afterPropertiesSet() {
        ENDPOINT = this.endpoint;
        ACCESS_KEY_ID = this.accessKeyId;
        ACCESS_KEY_SECRET = this.accessKeySecret;
        BUCKET_NAME = this.bucketName;
        WORD_BOOK_EXCEL_TEMPLATE = this.wordBookExcelTemplate;
    }
}
