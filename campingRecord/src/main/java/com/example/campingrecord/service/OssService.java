package com.example.campingrecord.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadImage(MultipartFile file);

    String getImageUrl(String imageUrl);
}
