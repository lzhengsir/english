package com.gcc.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @className: SmsUtils
 * @author: lzheng
 * @date: 2022/8/26 15:59
 * @description:
 */
@Component
@ConfigurationProperties(prefix = "sms.dingxin")
public class SmsUtils implements InitializingBean {

    private String appcode;

    public static String APPCODE;

    @Override
    public void afterPropertiesSet() {
        APPCODE = this.appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }
}
