package com.hualala.common;

public enum ResultCode {

    SUCCESS("000","执行成功"),
    LOGIN_LOST("001","登陆失效"),
    SYSTEM_ERROR("002","系统异常,请稍后重试"),
    PARAMS_LOST("003","缺少参数,请检查"),
    BUSINESS_ERROR("004","业务异常");



    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
