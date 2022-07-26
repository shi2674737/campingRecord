package com.example.campingrecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campingrecord.dto.LoginDto;
import com.example.campingrecord.dto.UserDto;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.vo.UserVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends IService<User> {
    String login(LoginDto loginDto);

    void addUser(UserDto.AddUserDto addUserDto);

    void updateUser(UserDto.UpdateUserDto updateUserDto);

    @Transactional
    void addUserRelation(Long userId);

    UserVo getUserByPhone(String phone);

    UserVo getUserDetail(Long id);

    List<UserVo> getFriendList();

    Page<UserVo> getFriendPage(UserDto.GetUserDto userDto);
}
