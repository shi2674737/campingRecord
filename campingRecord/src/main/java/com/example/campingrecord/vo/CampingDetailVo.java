package com.example.campingrecord.vo;

import com.example.campingrecord.entity.CampingUserRelation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CampingDetailVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 地点
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long addressId;
    /**
     * 地点
     */
    private String addressName;

    /**
     * 0 所有人可见 5 关联人可见
     */
    private Integer visibleStatus;

    /**
     * 0 所有人可见 5 关联人可见
     */
    private String visibleStatusStr;

    /**
     * 露营开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime campingStartTime;

    /**
     * 露营结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime campingEndTime;

    /**
     * 参与人
     */
    private String campingParticipant;

    /**
     * 花费总金额
     */
    private Long totalAmount;

    /**
     * 参与人数
     */
    private int peopleNum;

    /**
     * 天气
     */
    private String weather;

    /**
     * 备注
     */
    private String remark;


    // 图片url
    private List<CampingImageVo> images;

    // 关联的用户
    private List<CampingUserRelation> userRelations;

    private String comment;
    private BigDecimal score;
}
