package com.ning.constants.test;

import com.ning.constants.Worker.*;
import com.ning.constants.entity.LocationInfo;
import com.ning.constants.entity.MainInfo;
import com.ning.constants.entity.Store;
import com.ning.constants.entity.Wallet;
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
        MainInfo mainInfo = new MainWork().mainInfo();
        log.info("mainInfo = {}", mainInfo);

    }

    @Test
    public void testStore() {
        Store store = new StoreWorker().store();
        log.info("store = {}", store);
    }

    @Test
    public void testSort() {
        new SortWorker().sortIfNeed();
    }

    @Test
    public void testBuy() {
        boolean b = new StoreWorker().buyFromStore(1);
        System.out.println(b);
    }

    @Test
    public void testBalance() {
        Wallet balance = new CoinWorker().balance();
        if (balance != null) {
            System.out.println(balance.getCoin());
        }
    }

    @Test
    public void testMerge() {
        new MergeWorker().merge();
    }

    @Test
    public void testFindMerge() {
        LocationInfo[][] mergeTarget = new MergeWorker().findMergeTarget();
        System.out.println();
    }
}
