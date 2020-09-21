package com.ning.constants.entity;

import lombok.Data;

/**
 * @Author: nicholas
 * @Date: 2020/9/21 20:00
 * @Descreption:
 */
@Data
public class UserInfo {
    private long coin;
    private long coinSpeed;
    private String face;
    private int level;
    private String nickName;
}
