package com.example.campingrecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.entity.UserRelation;

import java.util.List;

public interface UserRelationService extends IService<UserRelation> {
    List<User> getFriendList(Long userId);
}
