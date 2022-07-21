package com.example.campingrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CampingRecordDto {
    @Data
    public static class AddCampingRecordDto {
        /**
         * 地点
         */
        private Long addressId;

        /**
         * 0 所有人可见 5 关联人可见
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
        private List<String> images;

        // 关联用户id
        private List<Long> relationUserId;

        /**
         * 评论
         */
        private String comment;

        /**
         * 评分
         */
        private BigDecimal score;
    }

    @Data
    public static class UpdateCampingRecordDto {
        private Long id;
        /**
         * 地点
         */
        private Long addressId;

        /**
         * 0 所有人可见 5 关联人可见
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
        private List<String> images;

        // 关联用户id
        private List<Long> relationUserId;

        /**
         * 评论
         */
        private String comment;

        /**
         * 评分
         */
        private BigDecimal score;
    }

    @Data
    public static class GetCampingRecordListDto {
        private Long userId;
        private Integer pageNo;
        private Integer pageSize;
    }

    @Data
    public static class GetCampingRecordDetailDto {
        private Long campingId;
    }
}
