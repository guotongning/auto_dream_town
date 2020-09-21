package com.ning.constants.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ning.constants.Worker.CoinWorker;
import com.ning.constants.Worker.MainWork;
import com.ning.constants.Worker.PlayWorker;
import com.ning.constants.entity.Wallet;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 不在能知，乃在能行 ——nicholas
 * @description
 * @date 2020/9/21 16:49
 */
@Slf4j
public class MyTimerTask {

    private static final int FIVE_MINUTE = 1000 * 60 * 5;

    /**
     * 报告执行状态以及房屋信息的定时任务
     */
    public static void cycleReport() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                log.info("----------------REPORT START----------------");
                log.info("RUN MODE = {}", PlayWorker.playMode);
                Wallet balance = CoinWorker.balance();
                if (balance != null) {
                    log.info("COIN BALANCE = {}", balance.getCoin());
                }
                log.info("MAIN INFO = {}", JSON.toJSONString(MainWork.mainInfo(), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat));
                log.info("-----------------REPORT END-----------------");
            }
        }, 0, FIVE_MINUTE);
    }
}
