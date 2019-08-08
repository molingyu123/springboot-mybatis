package com.example.demo.core.ret;

/**
 * 返回对象实体
 * @param <T>
 */
public class RetResult<T> {

    public int code;

    private String message;

    private T data;

    public RetResult<T> setCode(RetCode retCode){
        this.code =retCode.code;
        return this;
    }

    public int getCode(){
        return code;
    }

    public RetResult<T> setCode(int code){
        this.code=code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RetResult<T> setMessage(String message) {
        this.message = message;
        return  this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
