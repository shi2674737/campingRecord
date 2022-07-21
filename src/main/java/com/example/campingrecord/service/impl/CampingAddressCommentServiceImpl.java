package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.entity.CampingAddressComment;
import com.example.campingrecord.mapper.CampingAddressCommentMapper;
import com.example.campingrecord.service.CampingAddressCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CampingAddressCommentServiceImpl extends ServiceImpl<CampingAddressCommentMapper, CampingAddressComment>
        implements CampingAddressCommentService {
}
