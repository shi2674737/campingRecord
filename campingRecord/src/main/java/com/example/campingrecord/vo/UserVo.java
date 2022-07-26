package com.example.campingrecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class UserVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

}