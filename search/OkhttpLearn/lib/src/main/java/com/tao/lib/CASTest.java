package com.tao.lib;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by taowei on 2018/3/18.
 * 2018-03-18 19:43
 * OkhttpLearn
 * com.tao.lib
 */

public class CASTest {
    public static void main(String[] args){
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();

    }
}
