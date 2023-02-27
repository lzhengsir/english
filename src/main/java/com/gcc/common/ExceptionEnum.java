package com.gcc.common;

/**
 * @author: lzhen
 * @since: 2022/11/1 17:39
 * @description:
 */
public enum ExceptionEnum {
    /**
     * 枚举异常信息
     */
    STRING_CONVERTER_FOR_OBJECT_FAILED(ResultCode.ERROR, "字符串转换为对象失败"),
    WRITE_AS_JSON_STRING_FAILED(ResultCode.ERROR, "转换为JSONString失败"),
    PLEASE_UPLOAD_EXCEL(ResultCode.ERROR, "请上传excel文件"),
    ALREADY_SEND_MESSAGE(ResultCode.ERROR, "不可重复发送短信"),
    USER_ALREADY_REGISTER(ResultCode.ERROR, "用户已注册"),
    VERIFY_CODE_ERROR(ResultCode.ERROR, "验证码不正确"),
    USERNAME_OR_PWD_ERROR(ResultCode.ERROR, "用户名或密码错误"),
    PLEASE_LOGIN(ResultCode.AUTHORIZATION_ERROR, "请先登录"),
    WORD_NOT_EXIST(ResultCode.ERROR, "单词不存在"),
    THIS_WORD_NOT_EXIST(ResultCode.ERROR, "该单词不存在"),
    WORD_TAG_NOT_EXIST(ResultCode.ERROR, "该单词标记不存在"),
    THIS_WORD_ALREADY_EXIST(ResultCode.ERROR, "该单词已存在"),
    PLEASE_USE_TEMPLATE(ResultCode.ERROR, "请使用模板文件"),
    ILLEGAL_OPERATION(ResultCode.ERROR, "非法操作"),
    TWICE_PWD_NOT_SAME(ResultCode.ERROR, "两次密码不一致");
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 返回信息
     */
    private final String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
