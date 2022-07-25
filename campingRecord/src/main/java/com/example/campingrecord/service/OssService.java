package com.example.campingrecord.service;

import com.example.campingrecord.vo.UploadImageVo;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    UploadImageVo uploadImage(MultipartFile file);

    String getImageUrl(String imageUrl);
}
