package com.example.campingrecord.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {

    @Data
    public static class AddUserDto {
        @NotBlank
        private String account;

        @NotBlank
        private String password;

        @NotBlank
        private String mail;

        @NotBlank
        private String realName;

        @NotBlank
        private String username;

        @NotBlank
        private String phone;
    }

    @Data
    public static class UpdateUserDto {
        @NotBlank
        private String id;

        @NotBlank
        private String account;

        private String oldPassword;

        private String newPassword;

        @NotBlank
        private String mail;

        @NotBlank
        private String realName;

        @NotBlank
        private String username;

        @NotBlank
        private String phone;
    }


    @Data
    public static class GetUserDto {
        private Long userId;
        private Integer pageNo;
        private Integer pageSize;
    }
}
