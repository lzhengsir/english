package com.gcc.service;

/**
 * @className: SmsService
 * @author: lzheng
 * @date: 2022/8/26 15:56
 * @description:
 */
public interface SmsService {
    /**
     * 发送6位的短信
     *
     * @param phone
     * @return
     */
    boolean sendShortMessage(String phone);
}
