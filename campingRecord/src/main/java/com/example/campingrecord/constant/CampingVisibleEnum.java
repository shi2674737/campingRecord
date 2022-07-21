package com.example.campingrecord.constant;

/**
 * 露营记录可见配置
 *
 * @author scy
 */
public enum CampingVisibleEnum implements ConvertibleEnum<Integer> {

    ALL(0, "所有人可见", 0),
    UNREGISTERED(5, "编辑人可见", 1);

    private Integer code;
    private String name;
    private Integer sort;

    CampingVisibleEnum(Integer code, String name, Integer sort) {
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

    public static CampingVisibleEnum of(Integer value) {
        return ConvertibleEnum.of(CampingVisibleEnum.class, value);
    }
}

