package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class BiasedLockJOLTest2 {
    static A a;

    public static void main(String[] args) throws Exception {
        Thread.sleep(6000);
//        a = new A();
        List<A> list = new ArrayList<>();
        List<A> list2 = new ArrayList<>();
        List<A> list3 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new A());
            list2.add(new A());
            list3.add(new A());
        }
        out.println("初始状态" + 10 + ClassLayout.parseClass(A.class).toPrintable());//偏向锁

        Thread t1 = new Thread() {
            String name = "1";

            public void run() {
                out.printf(name);
                for (A a : list) {
                    synchronized (a) {
                        if (a == list.get(10))
                            out.println("t1 预期是偏向锁" + 10 + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                    }
                }
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        t1.start();
        Thread.sleep(5000);
        out.println("main 预期是偏向锁" + 10 + ClassLayout.parseInstance(list.get(10)).toPrintable());//偏向锁

        Thread t2 = new Thread() {
            String name = "2";

            public void run() {
                out.printf(name);

                for (int i = 0; i < 100; i++) {
                    A a = list.get(i);
                    synchronized (a) {
                        if (a == list.get(10)) {
                            out.println("t2 i=10 get(1)预期是无锁" + ClassLayout.parseInstance(list.get(1)).toPrintable());//偏向锁
                            out.println("t2 i=10 get(10) 预期轻量级锁 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                        }
                        if (a == list.get(19)) {
                            out.println("t2  i=19  get(10)预期是无锁" + 10 + ClassLayout.parseInstance(list.get(10)).toPrintable());//偏向锁
                            out.println("t2  i=19  get(19) 满足重偏向条件20 预期偏向锁 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                            out.println("A类的对象累计撤销达到20");
                        }

                    }
                }

                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        t2.start();

        Thread.sleep(5000);


        Thread t3 = new Thread() {
            String name = "3";

            public void run() {

                out.printf(name);
                for (A a : list2) {
                    synchronized (a) {
                        if (a == list2.get(10))
                            out.println("t3 预期是偏向锁" + 10 + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                    }
                }
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t3.start();
        Thread.sleep(5000);


        Thread t4 = new Thread() {
            String name = "4";

            public void run() {

                out.printf(name);

                for (int i = 0; i < 100; i++) {
                    A a = list2.get(i);
                    synchronized (a) {
                        if (a == list2.get(10)) {
                            out.println("t4 i=10 get(1)预期是无锁" + ClassLayout.parseInstance(list2.get(1)).toPrintable());//偏向锁
                            out.println("t4 i=10 get(10) 当前不满足重偏向条件 20 预期轻量级锁 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                        }
                        if (a == list2.get(19)) {
                            out.println("t4  i=19  get(10)预期是无锁" + 10 + ClassLayout.parseInstance(list2.get(10)).toPrintable());//偏向锁
                            out.println("t4 i=19 get(19) 当前满足重偏向条件 20 但A类的对象累计撤销达到40 预期轻量级锁 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                            out.println("A类的对象累计撤销达到40");
                        }
                        if (a == list2.get(20)) {
                            out.println("t4 i=20 get(20) 当前满足重偏向条件 20 预期轻量级锁 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁

                        }
                    }
                }

            }
        };
        t4.start();
        Thread.sleep(5000);


        out.println("main 预期是偏向锁" + 10 + ClassLayout.parseInstance(list3.get(0)).toPrintable());//偏向锁

        Thread t5 = new Thread() {
            String name = "5";

            public void run() {
                out.printf(name);
                for (A a : list3) {
                    synchronized (a) {
                        if (a == list3.get(10))
                            out.println("t5 预期是轻量级锁，A类的对象累计撤销达到40 不可以用偏向锁了" + 10 + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                    }
                }
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        t5.start();
        Thread.sleep(5000);
        out.println("main 预期是偏向锁" + 10 + ClassLayout.parseInstance(list.get(10)).toPrintable());//偏向锁

        Thread t6 = new Thread() {
            String name = "6";

            public void run() {
                out.printf(name);

                for (int i = 0; i < 100; i++) {
                    A a = list3.get(i);
                    synchronized (a) {
                        if (a == list3.get(10)) {
                            out.println("t6 i=10 get(1)预期是无锁" + ClassLayout.parseInstance(list3.get(1)).toPrintable());//偏向锁
                            out.println("t6 i=10 get(10) 预期轻量级锁 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁
                        }
                        if (a == list3.get(19)) {
                            out.println("t6  i=19  get(10)预期是无锁" + 10 + ClassLayout.parseInstance(list3.get(10)).toPrintable());//偏向锁
                            out.println("t6  i=19  get(19) 满足重偏向条件20 但A类的对象累计撤销达到40 不可以用偏向锁了 " + i + ClassLayout.parseInstance(a).toPrintable());//偏向锁

                        }

                    }
                }

                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        t6.start();

        Thread.sleep(5000);


        out.println("由于A撤销锁次数达到默认的 BiasedLockingBulkRevokeThreshold=40 这里实例化的对象 是无锁状态" + ClassLayout.parseInstance(new A()).toPrintable());//偏向锁
        out.println("由于B撤销锁次数没达到默认的 BiasedLockingBulkRevokeThreshold=40 这里实例化的对象 是偏向锁可以偏向状态 理论上可以再次验证上面A类的相关操作" + ClassLayout.parseInstance(new B()).toPrintable());//偏向锁
        out.println("撤销偏向后状态" + 10 + ClassLayout.parseClass(A.class).toPrintable());//偏向锁

    }


}