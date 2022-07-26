package com.example.campingrecord.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campingrecord.entity.User;
import com.example.campingrecord.entity.UserRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRelationMapper extends BaseMapper<UserRelation> {
    List<User> getFriendList(@Param("userId") Long userId);

}