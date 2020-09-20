package com.ning.constants.entity;

import lombok.Data;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 16:03
 * @Descreption:
 */
@Data
public class BuyResult {
    private long allCoin;
    private String coin;
    private int level;
    private String message;
    private long price;
    private int state;
}
