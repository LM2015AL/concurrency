package com.luban.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {

    private static final ReentrantLock reentrantLock = new ReentrantLock(true);

    public static void main(String[] args) {

        Thread t1 = new Thread() {
            @Override
            public void run() {

                reentrantLock.lock();
                System.out.println(Thread.currentThread().getId() + " 加锁成功");
                try {
                    reentrantLock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId() + " 加锁成功1");
                try {
                    TimeUnit.SECONDS.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
                System.out.println(Thread.currentThread().getId() + " 解锁成功2");
                reentrantLock.unlock();
                System.out.println(Thread.currentThread().getId() + " 解锁成功");
            }
        };
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread() {
            @Override
            public void run() {

                reentrantLock.lock();
                System.out.println(Thread.currentThread().getId() + " 加锁成功");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
                System.out.println(Thread.currentThread().getId() + " 解锁成功");
            }
        };
        t2.start();
    }
}
