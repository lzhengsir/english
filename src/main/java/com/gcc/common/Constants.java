package com.gcc.common;

import org.apache.commons.compress.utils.Sets;

import java.util.Set;

/**
 * @author: lzhen
 * @since: 2022/11/1 18:00
 * @description:
 */
public interface Constants {
    Integer BATCH_COUNT = 10000;
    /**
     * excel的数据类型
     */
    Set<String> EXCEL_SUFFIX_SET = Sets.newHashSet(".xls", ".xlsx", ".xlsm");
    /**
     * redis中的key的前缀
     */
    String REDIS_KEY_PREFIX = "english:word:";
    String USERID_NS = REDIS_KEY_PREFIX + "userid:";
    String REGISTER_NS = REDIS_KEY_PREFIX + "register:";
    /**
     * 预检请求的方法名称
     */
    String OPTIONS = "OPTIONS";
    /**
     * jwt中的SESSION_ID的Key
     */
    String SESSION_IDP_KEY_IN_JWT = "SESSION_ID";
    String COLON = ":";
}
