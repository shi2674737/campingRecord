package com.example.campingrecord.vo;

import lombok.Data;

@Data
public class CampingImageVo {

    private Long id;

    private Long campingId;

    private String imageUrl;

    private String imageFullUrl;
}
