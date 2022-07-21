package com.example.campingrecord.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

public class FastJsonUtil {
    public static Map<String, String> bean2Map(Object object) {
        return JSON.parseObject(JSON.toJSONString(object), new TypeReference<Map<String, String>>() {});
    }
}
