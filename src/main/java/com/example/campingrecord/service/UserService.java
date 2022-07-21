package com.example.campingrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campingrecord.dto.LoginDto;
import com.example.campingrecord.dto.UserDto;
import com.example.campingrecord.entity.User;

public interface UserService extends IService<User> {
    String login(LoginDto loginDto);

    void addUser(UserDto.AddUserDto addUserDto);
}
