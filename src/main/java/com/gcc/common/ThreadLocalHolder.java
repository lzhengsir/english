package com.gcc.common;

/**
 * @author: lzhen
 * @since: 2022/11/21 14:37
 * @description:
 */
public class ThreadLocalHolder {

    public static final ThreadLocal<String> USERID = new ThreadLocal<>();

    static {
        USERID.remove();
    }
}
