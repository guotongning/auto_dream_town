package com.ning.constants.entity;

import lombok.Data;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 15:29
 * @Descreption:
 */
@Data
public class Elements {
    private long coinSpeed;
    private int level;
    private int lockState;
    private String name;
    private long price;
    private int unlockLevel;
}
