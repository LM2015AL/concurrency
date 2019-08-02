package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JOLExample99 {

    private static final int LIST1_SIZE = 10;

    public static void main(String[] args) throws Exception {
        List<A> list1 = new ArrayList<A>();
        for (int i = 0; i < LIST1_SIZE ; i++) {

            list1.add(new A());
        }

        Thread t1 = new Thread() {
            @Override
            public void run() {

                System.out.println(this.getId() + " start");

                for (A a : list1) {

                    synchronized (a) {
                        if(a == list1.get(1)) {
                            System.out.println(this.getId() + " locking list1.get(1) " + ClassLayout.parseInstance(a).toPrintable());
                        }
                    }
                }

                System.out.println(this.getId() + " end");
                try {
                    TimeUnit.SECONDS.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread() {
            @Override
            public void run() {

                System.out.println(this.getId() + " start");

                for (A a : list1) {

                    boolean flag1 = false;
//                    if(a == list1.get(20)) { // 已经使用了轻量级锁的对象不可再次使用偏向锁
//                        a = list1.get(9);
//                        flag1 = true;
//                    }

                    if(a == list1.get(1)) {// 后续未加锁未使用的对象 依然是偏向线程1

                        System.out.println(this.getId() + " lock before list1.get(9) " + ClassLayout.parseInstance(list1.get(9)).toPrintable());
                    }

                    if(a == list1.get(3)) {

                        newT3();
                        return;
                    }

                    synchronized (a) {
                        if(a == list1.get(0)) {// 轻量
                            System.out.println(this.getId() + " locking list1.get(1) " + ClassLayout.parseInstance(a).toPrintable());
                        }
                        if(a == list1.get(2)) {// 只要偏向锁在某线程内撤销次数达到此阈值，就修改在class元数据——InstanceKlass中的epoch 是对象和元数据的epoch不一致，而使对象可以重偏向。
                            System.out.println(this.getId() + " locking list1.get(2) " + ClassLayout.parseInstance(a).toPrintable());
                        }
//                        if(flag1 && a == list1.get(9)) {
//                            System.out.println(this.getId() + " locking list1.get(20 -> 9) " + ClassLayout.parseInstance(a).toPrintable());
//                        }
                    }
                }

                System.out.println(this.getId() + " end");
                try {
                    TimeUnit.SECONDS.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private void newT3() {
                Thread t3 = new Thread() {
                    @Override
                    public void run() {

                        System.out.println(this.getId() + " start");

                        for (A a : list1) {

                            if(a == list1.get(5)) {
                                return;
                            }

                            if(a == list1.get(1)) {// 后续未加锁未使用的对象 依然是偏向线程1

                                System.out.println(this.getId() + " lock before list1.get(9) " + ClassLayout.parseInstance(list1.get(9)).toPrintable());
                            }

                            synchronized (a) {
                                if(a == list1.get(0)) {// 轻量
                                    System.out.println(this.getId() + " locking list1.get(0) " + ClassLayout.parseInstance(a).toPrintable());
                                }
                                if(a == list1.get(2)) {// 只要偏向锁在某线程内撤销次数达到此阈值，就修改在class元数据——InstanceKlass中的epoch 是对象和元数据的epoch不一致，而使对象可以重偏向。
                                    System.out.println(this.getId() + " locking list1.get(2) " + ClassLayout.parseInstance(a).toPrintable());
                                }
                            }
                        }

                        System.out.println(this.getId() + " end");
                        try {
                            TimeUnit.SECONDS.sleep(10000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t3.start();
                try {
                    t3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 发生过重偏向的对象不可再次重偏向
        t2.join();
        for (int i = 0; i < LIST1_SIZE; i++) {
            A a = list1.get(i);
            synchronized (a) {
                System.out.println("i=" + i + "   " + ClassLayout.parseInstance(a).toPrintable());
            }
        }
    }
}