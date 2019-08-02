package com.luban.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DemoRW {

    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);

    public static void main(String[] args) {

        Thread t1 = new Thread() {
            @Override
            public void run() {

                reentrantReadWriteLock.writeLock().lock();
                System.out.println(Thread.currentThread().getId() + " W加锁成功");
                reentrantReadWriteLock.readLock().lock();
                System.out.println(Thread.currentThread().getId() + " R加锁成功");
                try {
                    TimeUnit.SECONDS.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantReadWriteLock.writeLock().unlock();
                System.out.println(Thread.currentThread().getId() + " W解锁成功");
                reentrantReadWriteLock.readLock().unlock();
                System.out.println(Thread.currentThread().getId() + " R解锁成功");
            }
        };
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread t2 = new Thread() {
//            @Override
//            public void run() {
//
//                reentrantLock.lock();
//                System.out.println(Thread.currentThread().getId() + " 加锁成功");
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                reentrantLock.unlock();
//                System.out.println(Thread.currentThread().getId() + " 解锁成功");
//            }
//        };
//        t2.start();
    }
}
