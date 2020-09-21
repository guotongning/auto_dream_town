package com.ning.constants.entity;

import lombok.Data;

import java.util.Map;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:00
 * @Descreption:
 */
@Data
public class MainInfo {
    private Map<Integer, LocationInfo> locationInfo;
    private UserInfo userInfo;
}
