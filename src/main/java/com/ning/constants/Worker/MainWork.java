package com.ning.constants.Worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.entity.Constants;
import com.ning.constants.entity.LocationInfo;
import com.ning.constants.entity.MainInfo;
import com.ning.constants.entity.Response;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:00
 * @Descreption:
 */
@Slf4j
public class MainWork implements Worker {

    private static MainInfo mainInfo;
    //是否开启主页缓存
    public static boolean cache = true;

    public static void clearCache() {
        mainInfo = null;
    }

    /**
     * 获取主页信息
     *
     * @return
     */
    public static MainInfo mainInfo() {
        if (mainInfo != null && cache) {
            return mainInfo;
        }
        String json = HttpClientUtil.sendGetRequest(Constants.MAIN_INFO);
        Response<MainInfo> response = JSONObject.parseObject(json, new TypeReference<Response<MainInfo>>() {
        });
        if (response != null) {
            mainInfo = response.getResult();
        } else {
            log.info("获取主页信息异常");
        }
        return mainInfo;
    }

    /**
     * 获取空闲位置数量
     */
    public static int emptyLocationNum() {
        MainInfo mainInfo = mainInfo();
        int emptyLocationNum = 0;
        if (mainInfo != null) {
            Map<Integer, LocationInfo> locationInfo = mainInfo.getLocationInfo();
            for (LocationInfo value : locationInfo.values()) {
                if (value == null) {
                    emptyLocationNum++;
                }
            }
        }
        return emptyLocationNum;
    }
}
