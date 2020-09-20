package com.ning.constants.Worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.entity.Constants;
import com.ning.constants.entity.Response;
import com.ning.constants.entity.Wallet;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 16:18
 * @Descreption:
 */
@Slf4j
public class CoinWorker implements Worker {
    /**
     * 获取余额信息
     *
     * @return
     */
    public static Wallet balance() {
        String param = "https://dreamtowntz.58.com/web/dreamtown/income";
        String json = HttpClientUtil.sendPostRequest(Constants.BALANCE, param);
        Response<Wallet> response = JSONObject.parseObject(json, new TypeReference<Response<Wallet>>() {
        });
        if (response != null) {
            return response.getResult();
        } else {
            log.info("获取余额信息异常");
        }
        return null;
    }
}
