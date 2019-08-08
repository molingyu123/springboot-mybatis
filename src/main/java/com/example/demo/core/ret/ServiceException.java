package com.example.demo.core.ret;

import java.io.Serializable;

/**
 * 业务类异常
 */
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -5727596174564672286L;

    // 无参构造
    public ServiceException(){ }

    public ServiceException(String message){
        super(message);
    }
    public ServiceException(String message,Throwable cause){
        super(message,cause);
    }
}
