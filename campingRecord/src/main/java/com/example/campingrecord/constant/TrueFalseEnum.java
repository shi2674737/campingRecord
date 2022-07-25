package com.example.campingrecord.constant;

/**
 * 是否标识
 *
 * @author scy
 */
public enum TrueFalseEnum implements ConvertibleEnum<Integer> {

    TRUE(0, "不是", 0),
    FALSE(1, "是", 1);

    private Integer code;
    private String name;
    private Integer sort;

    TrueFalseEnum(Integer code, String name, Integer sort) {
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

    public static TrueFalseEnum of(Integer value) {
        return ConvertibleEnum.of(TrueFalseEnum.class, value);
    }
}

