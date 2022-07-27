package com.example.campingrecord.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campingrecord.dto.LoginDto;
import com.example.campingrecord.dto.UserDto;
import com.example.campingrecord.service.UserService;
import com.example.campingrecord.vo.UserVo;
import com.example.campingrecord.vo.result.UResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public UResult<String> login(@RequestBody LoginDto loginDto) {
        log.info("login start loginDto = {}", loginDto);
        String token = userService.login(loginDto);
        log.info("login success.");
        return UResult.ok().data(token);
    }

    @PostMapping("/addUser")
    public UResult<String> addUser(@RequestBody @Valid UserDto.AddUserDto addUserDto) {
        log.info("addUser start addUserDto = {}", addUserDto);
        userService.addUser(addUserDto);
        log.info("addUser success.");
        return UResult.ok();
    }

    @GetMapping("/addUserRelation")
    public UResult<String> addUserRelation(@RequestParam("userId") Long userId) {
        log.info("addUserRelation start userId = {}", userId);
        userService.addUserRelation(userId);
        log.info("addUserRelation success.");
        return UResult.ok();
    }

    @PostMapping("/updateUser")
    public UResult<String> updateUser(@RequestBody @Valid UserDto.UpdateUserDto updateUserDto) {
        log.info("updateUser start updateUserDto = {}", updateUserDto);
        userService.updateUser(updateUserDto);
        log.info("updateUser success.");
        return UResult.ok();
    }

    @GetMapping("/getUserDetail")
    public UResult<UserVo> getUserDetail(@RequestParam("id") Long id) {
        log.info("getUserDetail start id = {}", id);
        UserVo user = userService.getUserDetail(id);
        log.info("getUserDetail success. user = {}", user);
        return UResult.ok().data(user);
    }

    @GetMapping("/getUserByPhone")
    public UResult<UserVo> getUserByPhone(@RequestParam("phone") String phone) {
        log.info("getUserByPhone start phone = {}", phone);
        UserVo user = userService.getUserByPhone(phone);
        log.info("getUserByPhone success.");
        return UResult.ok().data(user);
    }

    @GetMapping("/getFriendList")
    public UResult<List<UserVo>> getFriendList() {
        log.info("getFriendList start.");
        List<UserVo> users = userService.getFriendList();
        log.info("getFriendList success.");
        return UResult.ok().data(users);
    }

    @GetMapping("/getFriendPage")
    public UResult<Page<UserVo>> getFriendPage(UserDto.GetUserDto userDto) {
        log.info("getFriendPage start.");
        Page<UserVo> users = userService.getFriendPage(userDto);
        log.info("getFriendPage success.");
        return UResult.ok().data(users);
    }
}
