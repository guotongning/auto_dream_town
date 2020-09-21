package com.ning.constants.entity.enums;

/**
 * 可供选择的三种模式。
 * 满员所需最低等级(second_low_level) = 最高级房屋等级 - 10 （一共有12个位置，按照等级顺序排列，最后一个等级的房屋需要两个。）
 * 可用于合成second_low_level的最低等级需求（first_low_level） =  second_low_level - 空闲位置 + 2
 * <p>
 * fast（9<剩余）: 低等级的房屋比较便宜，所以在房屋的空余位置足够的时候，购买低等级房屋去合成满员所需最低等级房屋。并且升级之后会伴随着房屋雨。速度快的一笔
 * <p>
 * normal（4<剩余<9）: 房屋位置空闲不是很多，但不至于购买房屋的时候只能购买second_low_level,比slow快一些，比fast慢一些。
 * <p>
 * slow（0<剩余<=4）: 剩余位置较少，购买房屋的时候有最低等级需求 = second_low_level
 */
public enum PlayMode {
    FAST,
    NORMAL,
    SLOW,
    ;
}
