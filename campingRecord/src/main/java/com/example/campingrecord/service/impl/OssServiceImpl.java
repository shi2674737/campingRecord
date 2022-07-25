package com.example.campingrecord.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.campingrecord.exception.BaseException;
import com.example.campingrecord.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class OssServiceImpl implements OssService {

    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.path}")
    private String path;

    /**
     * 上传图片返回相对路径
     * @param file file
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file) {
        OSS ossClient = getOSSClient();
        UUID uuid = UUID.randomUUID();
        String filename = file.getResource().getFilename();
        filename = path + uuid + filename.substring(filename.lastIndexOf("."));
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, new ByteArrayInputStream(file.getBytes()));
            ossClient.putObject(putObjectRequest);
            return filename;
        } catch (Exception ex) {
            log.error("updateImage failed. ex = ", ex);
            throw new BaseException("图片上传失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 通过相对路径获取完整url
     * @param imageUrl imageUrl
     * @return
     */
    @Override
    public String getImageUrl(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            return null;
        }
        OSS ossClient = getOSSClient();
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 2);
        try {
            return ossClient.generatePresignedUrl(bucketName, imageUrl, expiration).toString();
        } catch (Exception ex) {
            log.error("getImageUrl failed. imageUrl = {}, ex = ", imageUrl , ex);
            throw new BaseException("图片路径获取失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    private OSS getOSSClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
