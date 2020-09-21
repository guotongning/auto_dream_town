package com.ning.constants.Worker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.entity.*;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 15:00
 * @Descreption:
 */
@Slf4j
public class MergeWorker implements Worker {

    /**
     * 自动合并房屋
     * 1.制定合并计划
     * 2.按照计划进行合并
     * 3.至少会合并一组房屋，多组的时候有其中一组合并失败了就会返回false。
     */
    public static boolean merge() {
        return merge(findMergeTarget());
    }


    /**
     * 合并房屋
     *
     * @param infos
     */
    private static boolean merge(LocationInfo[][] infos) {
        for (LocationInfo[] info : infos) {
            if (info[0] != null && info[1] != null) {
                if (!merge(info[0].getLocationIndex(), info[1].getLocationIndex())) {
                    log.info("合并房屋失败！ fromId = {} toId = {}", info[0].getLocationIndex(), info[1].getLocationIndex());
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 调用合并房屋接口
     *
     * @param indexFrom
     * @param indexTo
     * @return
     */
    private static boolean merge(int indexFrom, int indexTo) {
        String param = "fromId=" + indexFrom + "&" + "toId=" + indexTo;
        String json = HttpClientUtil.sendPostRequest(Constants.MERGE, param);
        Response<Elements> response = JSONObject.parseObject(json, new TypeReference<Response<Elements>>() {
        });
        if (response != null) {
            Elements result = response.getResult();
            log.info("合并房屋完成 fromId = {},toId = {},合并结果： {}级-{}", indexFrom, indexTo, result.getLevel(), result.getName());
            MainWork.clearCache();
            return true;
        }
        return false;
    }

    /**
     * 检测可以合并的房屋
     */
    public static LocationInfo[][] findMergeTarget() {
        LocationInfo[][] ableMerge = new LocationInfo[Constants.CAPACITY / 2][2];
        MainInfo mainInfo = MainWork.mainInfo();
        if (mainInfo == null) {
            return ableMerge;
        }
        Set<Integer> hasUse = new HashSet<>();
        int index = 0;
        for (Map.Entry<Integer, LocationInfo> entry : mainInfo.getLocationInfo().entrySet()) {
            LocationInfo value = entry.getValue();
            if (value == null) {
                continue;
            }
            for (Map.Entry<Integer, LocationInfo> infoEntry : mainInfo.getLocationInfo().entrySet()) {
                LocationInfo locationInfo = infoEntry.getValue();
                if (locationInfo == null || hasUse.contains(value.getLocationIndex()) || hasUse.contains(locationInfo.getLocationIndex())) {
                    continue;
                }
                //可以合并
                if (locationInfo.getLevel() == value.getLevel() && locationInfo.getLocationIndex() != value.getLocationIndex()) {
                    ableMerge[index][0] = value;
                    ableMerge[index][1] = locationInfo;
                    index++;
                    hasUse.add(value.getLocationIndex());
                    hasUse.add(locationInfo.getLocationIndex());
                }
            }
        }
        log.info("制定房屋合并计划 : {}", JSON.toJSONString(ableMerge));
        return ableMerge;
    }

}
