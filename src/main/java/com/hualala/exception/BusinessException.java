package com.hualala.exception;

import com.hualala.common.ResultCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

	private String code;
    private String msg;

    public BusinessException(ResultCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }


}
