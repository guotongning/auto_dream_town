package com.ning.constants.Worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.core.PlayStarter;
import com.ning.constants.entity.BuyResult;
import com.ning.constants.entity.Constants;
import com.ning.constants.entity.Response;
import com.ning.constants.entity.Store;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:53
 * @Descreption:
 */
@Slf4j
public class StoreWorker implements Worker {

    private SortWorker sortWorker = new SortWorker();
    private MergeWorker mergeWorker = new MergeWorker();

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

    public static boolean buyFromStore(int level) {
        String param = "level=" + level + "&type=store";
        String json = HttpClientUtil.sendPostRequest(Constants.BUY, param);
        Response<BuyResult> response = JSONObject.parseObject(json, new TypeReference<Response<BuyResult>>() {
        });
        if (response != null) {
            BuyResult result = response.getResult();
            if (result != null && result.getMessage() == null) {
                log.info("房屋购买成功！ level = {},剩余金币 = {}", level, result.getAllCoin());
                return true;
            } else if (result != null && Constants.BUY_RES_1.equals(result.getMessage())) {
                //位置不足，合并房屋，或者出售。
                log.info("房屋购买失败! 原因 = {},剩余金币 = {}", result.getMessage(), result.getAllCoin());
                SortWorker.sortIfNeed();
                MergeWorker.merge();
            }
        }
        return false;
    }
}
