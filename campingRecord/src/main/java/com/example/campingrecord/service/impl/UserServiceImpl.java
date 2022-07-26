package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.dto.LoginDto;
import com.example.campingrecord.dto.UserDto;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.entity.UserRelation;
import com.example.campingrecord.exception.BaseException;
import com.example.campingrecord.mapper.UserMapper;
import com.example.campingrecord.service.UserRelationService;
import com.example.campingrecord.service.UserService;
import com.example.campingrecord.utils.FastJsonUtil;
import com.example.campingrecord.utils.JwtUtil;
import com.example.campingrecord.utils.MD5Util;
import com.example.campingrecord.utils.UserUtil;
import com.example.campingrecord.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    UserRelationService userRelationService;

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
    @Transactional
    public void addUser(UserDto.AddUserDto addUserDto) {
        // 新增用户
        User user = new User();
        BeanUtils.copyProperties(addUserDto, user);
        user.setPassword(MD5Util.getMD5Str(user.getPassword()));
        baseMapper.insert(user);
        // 互加好友
        addUserRelation(user.getId());
    }

    @Override
    public void updateUser(UserDto.UpdateUserDto updateUserDto) {
        String userId = updateUserDto.getId();
        User user = baseMapper.selectById(userId);
        BeanUtils.copyProperties(updateUserDto, user);
        // 如果有新旧密码就改密码
        if (StringUtils.isNotEmpty(updateUserDto.getOldPassword())
                && StringUtils.isNotEmpty(updateUserDto.getNewPassword())) {
            String oldPassword = MD5Util.getMD5Str(updateUserDto.getOldPassword());
            if (!user.getPassword().equals(oldPassword)) {
                throw new BaseException("原密码不正确");
            }
            user.setPassword(updateUserDto.getNewPassword());
        }
        baseMapper.updateById(user);
    }

    @Override
    @Transactional
    public void addUserRelation(Long userId) {
        // 互加好友
        UserRelation userRelation = new UserRelation();
        userRelation.setUserId(UserUtil.getCurrentUser().getId());
        userRelation.setRelationUserId(userId);
        userRelationService.save(userRelation);
        UserRelation userRelation2 = new UserRelation();
        userRelation2.setUserId(userId);
        userRelation2.setRelationUserId(UserUtil.getCurrentUser().getId());
        userRelationService.save(userRelation2);
    }

    @Override
    public UserVo getUserByPhone(String phone) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone, phone);
        User user = baseMapper.selectOne(userLambdaQueryWrapper);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public UserVo getUserDetail(Long id) {
        User user = baseMapper.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public List<UserVo> getFriendList() {
        Long userId = UserUtil.getCurrentUser().getId();
        List<User> friendList = userRelationService.getFriendList(userId);
        // 列表添加自己
        User self = baseMapper.selectById(userId);
        friendList.add(self);

        if (CollectionUtils.isEmpty(friendList)) {
            return new ArrayList<>();
        }
        return friendList.stream().map(friend -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(friend, userVo);
            return userVo;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<UserVo> getFriendPage(UserDto.GetUserDto userDto) {
        Page<UserVo> userVoPage = new Page<>();
        userVoPage.setCurrent(userDto.getPageNo());
        long start = getPageNo(userDto.getPageNo(), userDto.getPageSize());
        long end = start + userDto.getPageSize();
        // 获取所有
        List<UserVo> friendList = getFriendList();
        userVoPage.setTotal(friendList.size());
        friendList = friendList.stream()
                .skip(start)
                .limit(end)
                .collect(Collectors.toList());
        userVoPage.setRecords(friendList);
        return userVoPage;
    }

    private static long getPageNo(long pageNo,long pageSize){
        if (pageNo <= 1){
            return 0;
        }
        return (pageNo - 1) * pageSize;
    }
}
