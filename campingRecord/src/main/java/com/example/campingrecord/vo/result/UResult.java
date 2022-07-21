package com.example.campingrecord.vo.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author scy
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Data
public class UResult<T> implements Serializable {

    private static final long serialVersionUID = 7801311381954465730L;

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    /**
     * 构造器私有
     */
    private UResult() {
    }

    /**
     * 通用返回成功
     *
     * @return R
     */
    public static UResult ok() {
        UResult uResult = new UResult();
        uResult.setSuccess(ResultCode.SUCCESS.getSuccess());
        uResult.setCode(ResultCode.SUCCESS.getCode());
        uResult.setMessage(ResultCode.SUCCESS.getMessage());
        return uResult;
    }

    /**
     * 通用返回失败，未知错误
     *
     * @return com.gk.gkcommon.result.R
     * @author Administrator
     * @date 2021/7/8 13:16
     */
    public static UResult error() {
        UResult uResult = new UResult();
        uResult.setSuccess(ResultCode.UNKNOWN_ERROR.getSuccess());
        uResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
        uResult.setMessage(ResultCode.UNKNOWN_ERROR.getMessage());
        return uResult;
    }

    /**
     * 设置结果，形参为结果枚举
     *
     * @param result result
     * @return com.gk.gkcommon.result.R
     * @author Administrator
     * @date 2021/7/8 13:16
     */
    public static UResult setResult(ResultCode result) {
        UResult uResult = new UResult();
        uResult.setSuccess(result.getSuccess());
        uResult.setCode(result.getCode());
        uResult.setMessage(result.getMessage());
        return uResult;
    }

    /**------------使用链式编程，返回类本身-----------**/

    /**
     * 自定义返回数据
     *
     * @param data data
     * @return com.gk.gkcommon.result.R
     * @author Administrator
     * @date 2021/7/8 13:16
     */
    public UResult data(T data) {
        this.setData(data);
        return this;
    }

    /**
     * 自定义状态信息
     *
     * @param message message
     * @return com.gk.gkcommon.result.R
     * @author Administrator
     * @date 2021/7/8 13:17
     */
    public UResult message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     *
     * @param code code
     * @return com.gk.gkcommon.result.R
     * @author Administrator
     * @date 2021/7/8 13:17
     */
    public UResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回结果
     *
     * @param success success
     * @return com.gk.gkcommon.result.R
     * @author Administrator
     * @date 2021/7/8 13:18
     */
    public UResult success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
