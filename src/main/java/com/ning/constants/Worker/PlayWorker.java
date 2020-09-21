package com.ning.constants.Worker;

import com.ning.constants.entity.LocationInfo;
import com.ning.constants.entity.MainInfo;

import java.util.Map;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 18:18
 * @Descreption: 游戏
 */
public class PlayWorker {

    /**
     * 最主要的玩游戏逻辑
     */
    public static void run() {

    }

    /**
     * 获取可以购买的等级第二低的房子
     * second_low_level = highHouseLevel - 10
     * level: 15 14 13 12 11 10 09 08 07 06 05 05
     * index: 01 02 03 04 05 06 07 08 09 10 11 12
     * second_low_level = 15 - 10 = 5
     *
     * @return
     */
    public static int getSecondLowLevel() {
        MainInfo mainInfo = MainWork.mainInfo();
        int maxLevel = 0;
        if (mainInfo != null) {
            Map<Integer, LocationInfo> info = mainInfo.getLocationInfo();
            for (LocationInfo value : info.values()) {
                if (value != null && value.getLevel() > maxLevel) {
                    maxLevel = value.getLevel();
                }
            }
        }
        if (maxLevel <= 10) {
            return 1;
        }
        return maxLevel - 10;
    }

    /**
     * 获取可以购买的等级最低的房子
     * first_low_level = second_low_level - emptyPlaceNum + 2
     *
     * @return
     */
    public static int getFirstLowLevel() {
        int secondLowLevel = getSecondLowLevel();
        int emptyLocationNum = MainWork.emptyLocationNum();
        if (secondLowLevel < emptyLocationNum) {
            return 1;
        } else {
            return secondLowLevel - emptyLocationNum + 2;
        }
    }

    /**
     * 升级回调
     */
    public static void levelUpCallback() {
        //TODO 开启房屋雨，启动fast模式。持续x分钟，或者达到结束条件。

    }
}
