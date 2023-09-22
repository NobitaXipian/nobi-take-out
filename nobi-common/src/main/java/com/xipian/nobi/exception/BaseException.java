package com.xipian.nobi.exception;

/**
 * 业务异常
 */

//TODO 重构为错误码和错误信息区分异常
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

}
