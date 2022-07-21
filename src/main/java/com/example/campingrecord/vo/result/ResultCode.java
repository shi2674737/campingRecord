package com.example.campingrecord.vo.result;

import lombok.Getter;

/**
 * result
 *
 * @author scy
 * @name ResultCode
 * @date 2021-07-06 17:28
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
public enum ResultCode {
    SUCCESS(true, 200, "操作成功"),
    FAILURE(false, 400, "业务异常"),
    UN_AUTHORIZED(false, 401, "请求未授权"),
    UNKNOWN_ERROR(false, 500, "未知错误");
    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCode(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
