package com.example.campingrecord.vo;

import com.example.campingrecord.entity.CampingUserRelation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CampingDetailVo {
    /**
     * 地点
     */
    private Long addressId;

    /**
     * 0 所有人可见 5 关联人可见 9 仅自己可见
     */
    private Integer visibleStatus;

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
