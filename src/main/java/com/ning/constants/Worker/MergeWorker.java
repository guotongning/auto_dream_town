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
     */
    public static void merge() {
        merge(findMergeTarget());
    }


    /**
     * 合并房屋
     *
     * @param infos
     */
    public static void merge(LocationInfo[][] infos) {
        for (LocationInfo[] info : infos) {
            if (info[0] != null && info[1] != null) {
                merge(info[0].getLocationIndex(), info[1].getLocationIndex());
                //递归合并房屋
                merge();
            }
        }
        //先整理一下房子
        SortWorker.sortIfNeed();
        //查询能买得起的最贵的房子
        Elements ableBuy;
        while (true) {
            ableBuy = getCanBuyHouse();
            if (StoreWorker.buyFromStore(ableBuy.getLevel())) {
                merge();
            }
        }
    }

    private static Elements getCanBuyHouse() {
        //余额
        Wallet balance = CoinWorker.balance();
        long coin = balance.getCoin();
        //商店
        Store store = StoreWorker.store();
        Elements ableBuy = null;
        for (Elements element : store.getElements()) {
            if (element.getLockState() == 1) {
                break;
            }
            if (coin > element.getPrice() && element.getLevel() > 7) {
                ableBuy = element;
            }
        }
        if (ableBuy == null) {
            try {
                log.info("进入等待......");
                for (int i = 180; i > 0; i--) {
                    TimeUnit.SECONDS.sleep(1);
                    if (i % 10 == 0) {
                        balance = CoinWorker.balance();
                        if (balance != null) {
                            log.info("balance = {}", balance.getCoin());
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCanBuyHouse();
        }
        return ableBuy;
    }

    public static Elements merge(int indexFrom, int indexTo) {
        String param = "fromId=" + indexFrom + "&" + "toId=" + indexTo;
        String json = HttpClientUtil.sendPostRequest(Constants.MERGE, param);
        Response<Elements> response = JSONObject.parseObject(json, new TypeReference<Response<Elements>>() {
        });
        if (response != null) {
            Elements result = response.getResult();
            log.info("合并房屋完成 fromId = {},toId = {},合并结果： {}级-{}", indexFrom, indexTo, result.getLevel(), result.getName());
            return result;
        }
        return null;
    }

    /**
     * 检测可以合并的房屋
     */
    public static LocationInfo[][] findMergeTarget() {
        LocationInfo[][] ableMerge = new LocationInfo[6][2];
        MainInfo mainInfo = MainWork.mainInfo();
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
        log.info("检测可以合并房屋...可合并房屋 = {}", JSON.toJSONString(ableMerge));
        return ableMerge;
    }

}
