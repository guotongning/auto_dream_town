package com.ning.constants.Worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.entity.*;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:53
 * @Descreption:
 */
@Slf4j
public class StoreWorker implements Worker {

    /**
     * 获取商店信息
     *
     * @return
     */
    public static Store store() {
        String json = HttpClientUtil.sendGetRequest(Constants.STORE);
        Response<Store> response = JSONObject.parseObject(json, new TypeReference<Response<Store>>() {
        });
        if (response != null) {
            return response.getResult();
        } else {
            log.info("获取商店信息异常");
        }
        return null;
    }

    /**
     * 从商店买房
     *
     * @param level
     * @return
     */
    public static BuyResult buyFromStore(int level) {
        //TODO 这里需要改成Bean返回{购买状态，想购买的房屋的价格，购买状态描述}
        String param = "level=" + level + "&type=store";
        String json = HttpClientUtil.sendPostRequest(Constants.BUY, param);
        Response<BuyResult> response = JSONObject.parseObject(json, new TypeReference<Response<BuyResult>>() {
        });
        if (response != null) {
            BuyResult result = response.getResult();
            if (result != null && result.getMessage() == null) {
                log.info("房屋购买成功！ level = {},剩余金币 = {}", level, result.getAllCoin());
                MainWork.clearCache();
                return new BuyResult(level, Constants.SUCCESS, result.getPrice());
            } else if (result != null && Constants.BUY_RES_1.equals(result.getMessage())) {
                log.info("房屋购买失败! 原因 = {},level = {},剩余金币 = {}", result.getMessage(),level, result.getAllCoin());
                return new BuyResult(level, Constants.BUY_RES_1, result.getPrice());
            } else if (result != null && Constants.BUY_RES_2.equals(result.getMessage())) {
                log.info("房屋购买失败! 原因 = {},level = {},剩余金币 = {}", result.getMessage(),level, result.getAllCoin());
                return new BuyResult(level, Constants.BUY_RES_2, result.getPrice());
            }
        }
        return new BuyResult(level, "ERROR", -1);
    }

    /**
     * 获取目前能买的起的最贵的房子
     *
     * @return
     */
    public static Elements getCanBuyHouse() {
        //余额
        Wallet balance = CoinWorker.balance();
        Elements ableBuy = null;
        if (balance != null) {
            long coin = balance.getCoin();
            //商店
            Store store = StoreWorker.store();
            if (store != null) {
                for (Elements element : store.getElements()) {
                    if (element.getLockState() == 1) {
                        break;
                    }
                    if (coin >= element.getPrice()) {
                        ableBuy = element;
                    }
                }
            }
        }
        return ableBuy;
    }

}
