package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.entity.UserRelation;
import com.example.campingrecord.mapper.UserRelationMapper;
import com.example.campingrecord.service.UserRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation>
        implements UserRelationService {

    @Override
    public List<User> getFriendList(Long userId) {
        return baseMapper.getFriendList(userId);
    }
}
