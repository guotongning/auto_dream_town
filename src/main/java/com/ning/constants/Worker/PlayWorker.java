package com.ning.constants.Worker;

import com.ning.constants.entity.*;
import com.ning.constants.entity.enums.PlayMode;
import com.ning.constants.task.MyTimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 18:18
 * @Descreption: 游戏
 */
@Slf4j
public class PlayWorker {

    /**
     * 当前运行模式 default : SLOW
     */
    public static PlayMode playMode = PlayMode.SLOW;

    /**
     * 异常retry次数
     */
    public static int EXCEPTION_RETRY = 0;

    /**
     * 最主要的玩游戏逻辑
     */
    public static void run() {
        try {
            log.info("===================GAME START===================");
            //启动定时任务
            MyTimerTask.cycleReport();
            //开始业务
            while (true) {
                while (MergeWorker.merge()) {

                }
                while (true) {
                    int firstLowLevel = getFirstLowLevel();
                    BuyResult buyResult = StoreWorker.buyFromStore(firstLowLevel);
                    if (buyResult.getMessage().equals(Constants.SUCCESS)) {
                        while (MergeWorker.merge()) {

                        }
                    }
                    if (buyResult.getMessage().equals(Constants.BUY_RES_2)) {
                        long price = buyResult.getPrice();
                        Wallet balance = CoinWorker.balance();
                        if (balance != null) {
                            //差多钱
                            long diffPrice = price - balance.getCoin();
                            MainInfo mainInfo = MainWork.mainInfo();
                            if (mainInfo != null) {
                                UserInfo userInfo = mainInfo.getUserInfo();
                                long second = diffPrice / userInfo.getCoinSpeed();
                                second = second == 0 ? 1 : second;
                                //等钱
                                try {
                                    log.info("进入等待，预计时间 = {}", second);
                                    TimeUnit.SECONDS.sleep(second);
                                } catch (InterruptedException e) {
                                    log.error(e.getMessage(), e);
                                }
                            }
                        }
                    }
                    if (buyResult.getMessage().equals(Constants.BUY_RES_1)) {
                        //整理垃圾房屋
                        SortWorker.sortIfNeed();
                    }

                }
            }
        } catch (Exception e) {
            if (++EXCEPTION_RETRY <= Constants.EXCEPTION_RETRY) {
                log.error("运行异常!尝试重启！重试第" + EXCEPTION_RETRY + "次 {}", e.getMessage(), e);
                run();
            } else {
                log.error("运行异常!程序终止！" + e.getMessage(), e);
                log.info("===================GAME OVER===================");
            }
        }
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
        int emptyNum = Math.max(MainWork.emptyLocationNum(), MainWork.emptyLocationNumNormal());
        int minLevel = MainWork.minLevel();
        if (emptyNum == 1) {
            return minLevel;
        }
        if (emptyNum >= 2) {
            return Math.min(secondLowLevel - emptyNum + 2, minLevel);
        } else {
            return 1;
        }
    }

    /**
     * 升级回调
     */
    public static void levelUpCallback() {
        //TODO 开启房屋雨，启动fast模式。持续x分钟，或者达到结束条件。房屋雨可能会伴随着宝箱？房屋雨期间注意关闭MainInfo缓存

    }
}
