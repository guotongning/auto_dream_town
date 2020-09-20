package com.ning.constants.Worker;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ning.constants.entity.Constants;
import com.ning.constants.entity.MainInfo;
import com.ning.constants.entity.Response;
import com.ning.constants.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:00
 * @Descreption:
 */
@Slf4j
public class MainWork implements Worker {

    /**
     * 获取主页信息
     *
     * @return
     */
    public static MainInfo mainInfo() {
        String json = HttpClientUtil.sendGetRequest(Constants.MAIN_INFO);
        Response<MainInfo> response = JSONObject.parseObject(json, new TypeReference<Response<MainInfo>>() {
        });
        if (response != null) {
            return response.getResult();
        } else {
            log.info("获取主页信息异常");
        }
        return null;
    }
}
