package com.ning.constants.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 16:03
 * @Descreption:
 */
@Data
@NoArgsConstructor
public class BuyResult {
    private long allCoin;
    private String coin;
    private int level;
    private String message;
    private long price;
    private int state;

    public BuyResult(int level, String message, long price) {
        this.level = level;
        this.message = message;
        this.price = price;
    }
}
