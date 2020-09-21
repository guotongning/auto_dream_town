package com.ning.constants.Worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.entity.*;
import com.ning.constants.entity.enums.PlayMode;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 16:21
 * @Descreption:
 */
@Slf4j
public class SortWorker implements Worker {

    /**
     * 整理房屋，如果需要的话。条件：无法进行merge，且位置满了。
     */
    public static boolean sortIfNeed() {
        LocationInfo[][] mergeTarget = MergeWorker.findMergeTarget();
        //判断是否有房屋可以进行合并，如果有就不卖房。
        for (LocationInfo[] locationInfos : mergeTarget) {
            //代表有房屋可以合并
            if (locationInfos[0] != null && locationInfos[1] != null) {
                return false;
            }
        }
        if (getEmptyPlaceNum() == 0) {
            sellWasteHouse();
        }
        return true;
    }

    private static int getEmptyPlaceNum() {
        int emptyPlaceNum = 0;
        MainInfo mainInfo = MainWork.mainInfo();
        if (mainInfo != null) {
            for (Map.Entry<Integer, LocationInfo> entry : mainInfo.getLocationInfo().entrySet()) {
                if (entry.getValue() == null) {
                    emptyPlaceNum++;
                }
            }
        }
        return emptyPlaceNum;
    }

    /**
     * 垃圾房屋定义：min level
     */
    public static void sellWasteHouse() {
        MainInfo mainInfo = MainWork.mainInfo();
        LocationInfo min = null;
        if (mainInfo != null) {
            for (LocationInfo value : mainInfo.getLocationInfo().values()) {
                if (min == null && value != null) {
                    min = value;
                    continue;
                }
                if (value != null && value.getLevel() < min.getLevel()) {
                    min = value;
                }
            }
        }
        if (min != null) {
            sellHouse(min.getLocationIndex(), min.getLevel());
        }
    }

    /**
     * 出售房屋
     *
     * @param location
     * @param level
     * @return
     */
    public static boolean sellHouse(int location, int level) {
        String param = "locationIndex=" + location;
        String json = HttpClientUtil.sendPostRequest(Constants.SELL, param);
        Response<Wallet> response = JSONObject.parseObject(json, new TypeReference<Response<Wallet>>() {
        });
        if (response != null) {
            log.info("房屋出售成功，level = {},获得金币{}", level, response.getResult().getCoin());
            MainWork.clearCache();
            return true;
        } else {
            log.info("获取房屋出售信息异常");
            return false;
        }
    }
}
