package com.example.demo.core.ret;

/**
 * 封装结果生成响应的对象
 */
public class RetResponse {

    private final static String SUCCESS="success";

    public static <T> RetResult<T> makeOKRsp(){
        return new RetResult<T>().setCode(RetCode.SUCCESS).setMessage(SUCCESS);
    }

    public static <T> RetResult<T> makeOKRsp(T data){
        return new RetResult<T>().setCode(RetCode.SUCCESS).setMessage(SUCCESS).setData(data);
    }

    public static <T> RetResult<T> makeErrRsp(String message){
        return new RetResult<T>().setCode(RetCode.FAIL).setMessage(message);
    }

    public static <T> RetResult<T> makeRsp(int code,String msg){
        return new RetResult<T>().setCode(code).setMessage(msg);
    }

    public static <T> RetResult<T> makeRsp(int code,String msg,T data){
        return new RetResult<T>().setCode(code).setMessage(msg).setData(data);
    }
}
