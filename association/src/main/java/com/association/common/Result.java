package com.association.common;

import java.io.Serializable;

public class Result<T> implements Serializable {
    /*    序列化操作时会把系统当前类的serialVersionUID写入到序列化文件中，当反序列化时系统会自动检测文件中的serialVersionUID，
        判断它是否与当前类中的serialVersionUID一致。如果一致说明序列化文件的版本与当前类的版本是一样的，可以反序列化成功，否则就失败；*/
    private static final long serialVersionUID = 1L;
    private T data;

    private Integer code;

    private String message;


    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }
    public Result() {}

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result(BizExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMsg();
    }
    public boolean isSuccess(){
        return this.getCode()==BizExceptionEnum.SUCCESS.getCode();
    }
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(BizExceptionEnum.SUCCESS.getCode(), message, data);
    }
    public static <T> Result<T> success(String message) {
        return new Result<>(BizExceptionEnum.SUCCESS.getCode(), message);
    }

    public static <T> Result<T> success(T data) {
        return success(BizExceptionEnum.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> success() {
        return success(BizExceptionEnum.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(BizExceptionEnum.FAIL.getCode(), message);
    }
    public static <T> Result<T> fail(String message, T data) {
        return new Result<>(BizExceptionEnum.FAIL.getCode(), message, data);
    }
    public static <T> Result<T> fail(){return fail(BizExceptionEnum.FAIL.getMsg(),null);}
}
