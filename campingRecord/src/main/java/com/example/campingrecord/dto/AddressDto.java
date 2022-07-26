package com.example.campingrecord.dto;

import lombok.Data;

@Data
public class AddressDto {
    @Data
    public static class AddAddressDto {
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
         * 关于使用卡式炉
         */
        private String sycls;

        /**
         * 关于使用碳
         */
        private String carbon;

        /**
         * 关于过夜
         */
        private String overnight;

        /**
         * 优点
         */
        private String advantage;

        /**
         * 缺点
         */
        private String disadvantage;
    }

    @Data
    public static class UpdateAddressDto extends AddAddressDto {
        private Long id;
    }

    @Data
    public static class GetAddressListDto {
        private Integer pageNo;
        private Integer pageSize;
    }

    @Data
    public static class GetAddressDetailDto {
        private Long id;
    }

}
