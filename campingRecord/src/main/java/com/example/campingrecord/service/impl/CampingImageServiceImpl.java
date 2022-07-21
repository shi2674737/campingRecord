package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.entity.CampingImage;
import com.example.campingrecord.mapper.CampingImageMapper;
import com.example.campingrecord.service.CampingImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CampingImageServiceImpl extends ServiceImpl<CampingImageMapper, CampingImage>
        implements CampingImageService {
}
