package com.example.campingrecord.controller;


import com.example.campingrecord.service.OssService;
import com.example.campingrecord.vo.result.UResult;
import com.example.campingrecord.vo.UploadImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/oss")
public class OssController {

    @Autowired
    OssService ossService;

    @PostMapping("/uploadImage")
    public UResult<UploadImageVo> uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("uploadImage start.");
        UploadImageVo uploadImageVo = ossService.uploadImage(file);
        log.info("uploadImage done.");
        return UResult.ok().data(uploadImageVo);
    }
}
