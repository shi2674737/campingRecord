package com.example.campingrecord.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.campingrecord.exception.BaseException;
import com.example.campingrecord.exception.TokenException;
import com.example.campingrecord.utils.ExceptionUtil;
import com.example.campingrecord.vo.result.ResultCode;
import com.example.campingrecord.vo.result.UResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 全局异常处理
 *
 * @author Administrator
 * @name GlobalExceptionHandler
 * @date 2021-07-01 16:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * -------- 通用异常处理方法 --------
     **/
    @ExceptionHandler(Exception.class)
    public UResult error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        return UResult.error();
    }

    /**
     * -------- 指定异常处理方法 --------
     **/
    @ExceptionHandler(NullPointerException.class)
    public UResult error(NullPointerException e) {
        log.error(ExceptionUtil.getMessage(e));
        return UResult.setResult(ResultCode.UNKNOWN_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public UResult error(HttpClientErrorException e) {
        log.error(ExceptionUtil.getMessage(e));
        return UResult.setResult(ResultCode.UNKNOWN_ERROR);
    }

    @ExceptionHandler(TokenException.class)
    public UResult error(TokenException e) {
        return UResult.setResult(ResultCode.UN_AUTHORIZED).message(e.getDefaultMessage());
    }

    /**
     * JwtUtil.verify(token);抛出该异常
     * @param e e
     * @return
     */
    @ExceptionHandler(TokenExpiredException.class)
    public UResult error(TokenExpiredException e) {
        log.error(ExceptionUtil.getMessage(e));
        return UResult.setResult(ResultCode.UN_AUTHORIZED);
    }

    /**
     * JwtUtil.verify(token);抛出该异常
     * @param e e
     * @return
     */
    @ExceptionHandler(JWTDecodeException.class)
    public UResult error(JWTDecodeException e) {
        log.error(ExceptionUtil.getMessage(e));
        return UResult.setResult(ResultCode.UN_AUTHORIZED);
    }

    @ExceptionHandler(BaseException.class)
    public UResult error(BaseException e) {
        return UResult.setResult(ResultCode.FAILURE).message(e.getDefaultMessage());
    }

}
