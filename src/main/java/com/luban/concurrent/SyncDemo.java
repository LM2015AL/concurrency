package com.luban.concurrent;

public class SyncDemo {

    private static final int SLEEP_TIME = 10000;

    public static void main(String[] args) {

        Thread thread = new Thread() {

            @Override
            public void run() {
//                while(!Thread.interrupted()) {
                while(!isInterrupted()) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        interrupt();
                        System.out.println("InterruptedException:" + isInterrupted());
//                        e.printStackTrace();
                    }
                }
                System.out.println("线程退出:" + isInterrupted());
            }
        };
        thread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
