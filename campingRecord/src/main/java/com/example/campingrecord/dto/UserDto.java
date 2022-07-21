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
    }
}
