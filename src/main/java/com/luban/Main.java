package com.luban;

import java.util.concurrent.CountDownLatch;

public class Main {

    public static int x = 20;
    public static volatile boolean flag = false;
    public static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) {

        Thread t2 = new Thread() {
            @Override
            public void run() {
                if(flag) {
                    System.out.println(x);
                }
            }
        };
        Thread t1 = new Thread() {
            @Override
            public void run() {
                flag = true;
                System.out.println("t1");
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                x = 40;
            }
        };

        for (int i = 0; i < 100; i++) {
            new Thread(t1).start();
            new Thread(t2).start();
        }
    }
}
