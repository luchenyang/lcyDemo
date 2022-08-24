package com.lcy.demo.log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/8/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModel<T> {

    public static final String successCode = "0000";

    //签名异常
    public static final String signCode = "0001";

    //参数异常
    public static final String argumentCode = "0002";

    //业务异常
    public static final String bussinessCode = "0003";

    //下载失败
    public static final String downloadFailCode = "0004";

    public static final String failCode = "9999";


    private String code;
    private String msg;
    private T data;

    public static ResponseModel build(String code, String msg) {
        ResponseModel build = ResponseModel.builder().code(code).msg(msg).build();
        return build;
    }

    public static ResponseModel fail(String msg) {
        ResponseModel build = ResponseModel.builder().code(failCode).msg(msg).build();
        return build;
    }

    public static ResponseModel fail(String code, String msg) {
        ResponseModel build = ResponseModel.builder().code(code).msg(msg).build();
        return build;
    }

    public static ResponseModel success(String msg, Object data) {
        ResponseModel build = ResponseModel.builder().code(successCode).data(data).build();
        return build;
    }
}