package com.example.campingrecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CampingImageVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private Long campingId;

    private String name;

    private String url;
}
