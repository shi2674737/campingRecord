package com.example.campingrecord.controller;


import com.example.campingrecord.dto.LoginDto;
import com.example.campingrecord.dto.UserDto;
import com.example.campingrecord.service.UserService;
import com.example.campingrecord.vo.result.UResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
