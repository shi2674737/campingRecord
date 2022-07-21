package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.dto.LoginDto;
import com.example.campingrecord.dto.UserDto;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.exception.BaseException;
import com.example.campingrecord.mapper.UserMapper;
import com.example.campingrecord.service.UserService;
import com.example.campingrecord.utils.FastJsonUtil;
import com.example.campingrecord.utils.JwtUtil;
import com.example.campingrecord.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Override
    public String login(LoginDto loginDto) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, loginDto.getAccount());
        User user = baseMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            throw new BaseException("账号错误");
        }
        String md5Str = MD5Util.getMD5Str(loginDto.getPassword());
        if (StringUtils.isEmpty(user.getPassword()) || !user.getPassword().equals(md5Str)) {
            throw new BaseException("密码不正确");
        }
        Map<String, String> userMap = FastJsonUtil.bean2Map(user);
        return JwtUtil.getToken(userMap);
    }

    @Override
    public void addUser(UserDto.AddUserDto addUserDto) {
        User user = new User();
        BeanUtils.copyProperties(addUserDto, user);
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        baseMapper.insert(user);
    }
}
