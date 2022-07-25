package com.example.campingrecord.vo;

import com.example.campingrecord.entity.CampingAddressComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AddressDetailVo {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 收费标准
     */
    private String cost;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 能否使用卡式炉
     */
    private Integer sycls;

    /**
     * 能否使用碳
     */
    private Integer carbon;

    /**
     * 能否过夜
     */
    private Integer overnight;

    /**
     * 优点
     */
    private String advantage;

    /**
     * 缺点
     */
    private String disadvantage;

    private LocalDateTime createTime;

    private Long createBy;

    private LocalDateTime updateTime;

    private Long updateBy;

    /**
     * 平均分
     */
    private BigDecimal avgScore;

    private List<CampingAddressComment> comments;
}
