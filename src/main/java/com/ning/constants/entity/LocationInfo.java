package com.ning.constants.entity;

import lombok.Data;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:42
 * @Descreption:
 */
@Data
public class LocationInfo implements Comparable<LocationInfo> {
    private long coinSpeed;
    private int level;
    private int locationIndex;
    private int state;
    private int type;

    public int compareTo(LocationInfo o) {
        return Integer.compare(this.getLevel(), o.getLevel());
    }
}
