package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.entity.CampingUserRelation;
import com.example.campingrecord.mapper.CampingUserRelationMapper;
import com.example.campingrecord.service.CampingUserRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CampingUserRelationServiceImpl extends ServiceImpl<CampingUserRelationMapper, CampingUserRelation>
        implements CampingUserRelationService {
}
