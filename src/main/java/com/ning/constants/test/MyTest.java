package com.ning.constants.test;

import com.alibaba.fastjson.JSON;
import com.ning.constants.Worker.*;
import com.ning.constants.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 14:45
 * @Descreption:
 */
@Slf4j
public class MyTest {
    @Test
    public void testMainInfo() {
        MainInfo mainInfo = MainWork.mainInfo();
        log.info("mainInfo = {}", mainInfo);
    }

    @Test
    public void testStore() {
        Store store = StoreWorker.store();
        log.info("store = {}", store);
    }

    @Test
    public void testSort() {
        boolean b = SortWorker.sortIfNeed();
        log.info("sort = {}", b);
    }

    @Test
    public void testBuy() {
        boolean b = StoreWorker.buyFromStore(1);
        log.info("sort = {}", b);
    }

    @Test
    public void testBalance() {
        Wallet balance = CoinWorker.balance();
        if (balance != null) {
            log.info("balance = {}", balance.getCoin());
        }
    }

    @Test
    public void testMerge() {
        boolean b = MergeWorker.merge();
        log.info("merge = {}", b);
    }

    @Test
    public void testFindMerge() {
        LocationInfo[][] mergeTarget = MergeWorker.findMergeTarget();
        log.info("mergeTarget = {}", JSON.toJSONString(mergeTarget));
    }

    @Test
    public void testCanBuyHouse() {
        Elements canBuyHouse = StoreWorker.getCanBuyHouse();
        log.info("canBuyHouse = {}", JSON.toJSONString(canBuyHouse));
    }
}
