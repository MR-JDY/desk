package com.uni.desk.base;


import java.io.Serializable;

/**
 * @Author: 刘建文
 * @Date: Created in 17:41 2020/03/04
 * @Description: 统一的后端返回报文
 */
public class ResultMsg<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public final static String SUCCESS_MESSAGE = "SUCCESS";
    public final static String SYSTEM_ERROR = "系统异常";

    public ResultMsg() {}

    //--------------------------------------成功--------------------------------------
    public static ResultMsg SUCCESS = new ResultMsg(CommonErrorCode.COMMON_SUCCESS.getCode(), SUCCESS_MESSAGE);

    public static ResultMsg SUCCESS(Object obj) {
        return new ResultMsg<>(CommonErrorCode.COMMON_SUCCESS.getCode(), SUCCESS_MESSAGE, obj);
    }

    //--------------------------------------权限登陆类--------------------------------------
    //权限拒绝
    public static ResultMsg AccessDenied = new ResultMsg(CommonErrorCode.ACCESS_DENIED.getCode(), CommonErrorCode.ACCESS_DENIED.getMessage());
    //未登陆
    public static ResultMsg NoLogin = new ResultMsg(CommonErrorCode.NO_LOGIN.getCode(), CommonErrorCode.NO_LOGIN.getMessage());


    //--------------------------------------通用系统异常--------------------------------------
    public static ResultMsg ERROR = new ResultMsg(CommonErrorCode.COMMON_ERROR.getCode(), SYSTEM_ERROR);

    public static ResultMsg ERROR(Object obj) {
        return new ResultMsg(CommonErrorCode.COMMON_ERROR.getCode(), SYSTEM_ERROR, obj);
    }

    public static ResultMsg ERROR(int code, String msg, Object obj) {
        return new ResultMsg(code, msg, obj);
    }

    public static ResultMsg ERROR(int code, String msg) {
        return new ResultMsg(code, msg);
    }

    public static ResultMsg ERROR(String msg) {
        return new ResultMsg(CommonErrorCode.COMMON_ERROR.getCode(), msg);
    }

    public static ResultMsg ERROR(String msg, Object obj) {
        return new ResultMsg(CommonErrorCode.COMMON_ERROR.getCode(), msg, obj);
    }


    //--------------------------------------当前系统时间未开放(目前内购市场用)--------------------------------------
    public static ResultMsg NoOpen = new ResultMsg(CommonErrorCode.SYSTEM_NO_OPEN.getCode(), CommonErrorCode.SYSTEM_NO_OPEN.getMessage());

    public static ResultMsg NoOpen(Object obj) {
        return new ResultMsg(CommonErrorCode.SYSTEM_NO_OPEN.getCode(), CommonErrorCode.SYSTEM_NO_OPEN.getMessage(), obj);
    }


    public ResultMsg(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMsg(CommonBusinessException e) {
        this.code = e.getCommonErrorCode().getCode();
        this.msg = e.getCommonErrorCode().getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
