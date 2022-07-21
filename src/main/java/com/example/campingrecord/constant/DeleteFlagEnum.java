package com.example.campingrecord.constant;

/**
 * 删除标识
 *
 * @author scy
 */
public enum DeleteFlagEnum implements ConvertibleEnum<Integer> {

    NORMAL(0, "正常", 0),
    DELETED(1, "已删除", 1);

    private Integer code;
    private String name;
    private Integer sort;

    DeleteFlagEnum(Integer code, String name, Integer sort) {
        this.code = code;
        this.name = name;
        this.sort = sort;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getSort() {
        return sort;
    }

    @Override
    public Integer value() {
        return this.code;
    }

    public static DeleteFlagEnum of(Integer value) {
        return ConvertibleEnum.of(DeleteFlagEnum.class, value);
    }
}

