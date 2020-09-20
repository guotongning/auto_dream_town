package com.ning.constants.core;

import com.ning.constants.Worker.MergeWorker;
import org.junit.Test;

/**
 * @Author: nicholas
 * @Date: 2020/9/20 18:18
 * @Descreption: 游戏
 */
public class PlayStarter {

    @Test
    public void start() {
        new MergeWorker().merge();
    }
}
