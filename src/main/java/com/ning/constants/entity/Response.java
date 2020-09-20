package com.ning.constants.entity;

import lombok.Data;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:00
 * @Descreption:
 */
@Data
public class Response<T> {
    private int code;
    private String message;
    private T result;
}
