package com.bcx.mylodic.exception;


/**
 * 自定义序列号异常
 *
 * @author zl
 */
public class SerialException extends RuntimeException {

    public SerialException(){
        super();
    }

    public SerialException(String msg){
        super(msg);
    }

    public SerialException(String msg,Throwable cause){
        super(msg,cause);
    }

    public SerialException(Throwable cause){
        super(cause);
    }
}
