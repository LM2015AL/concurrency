package com.luban.layout;

import java.util.concurrent.CountDownLatch;

// 无锁 1479ms    26ms
// 偏向 5061ms    73ms
// 轻量 43158ms   474ms
// 重量 49345ms   501ms
//‐XX:BiasedLockingStartupDelay=20000 ‐XX:BiasedLockingStartupDelay=0
public class JOLExample4 {
    static A a;
    static int max = 10000000;
    static CountDownLatch countDownLatch = new CountDownLatch(max);
    public static void main(String[] args) throws Exception {
        a = new A();
        long start = System.currentTimeMillis();
        parse1();
        long end = System.currentTimeMillis();
        System.out.println(String.format("%sms", end - start));
        System.out.println(a.i);
    }

    public static void parse0() {
        for (int i = 0; i < max; i++) {
            a.parse0();
        }
    }

    public static void parse1() {

        //调用同步方法1000000000L 来计算1000000000L的++，对比偏向锁和轻量级锁的性能
        //如果不出意外，结果灰常明显
        for (int i = 0; i < max; i++) {
            a.parse1();
        }
    }

    public static void parse2() throws Exception  {
        for(int i=0;i<2;i++){
            new Thread(){
                @Override
                public void run() {
                    while (countDownLatch.getCount() > 0) {
                        a.parse2();
                    }
                }
            }.start();
        }
        countDownLatch.await();
    }
}