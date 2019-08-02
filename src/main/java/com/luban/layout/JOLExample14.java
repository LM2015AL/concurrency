package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class JOLExample14 {

    static A a;
    public static void main(String[] args) throws Exception {
        a = new A();
//        synchronized (a) {
            out.println("main lock " + ClassLayout.parseInstance(a).toPrintable());
//        }

        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    out.println("t1 lock " + ClassLayout.parseInstance(a).toPrintable());
                }
//                try {
//                    TimeUnit.SECONDS.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        };
        t1.start();
//        t1.join();
        TimeUnit.SECONDS.sleep(5);
        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    out.println("t2 lock " + ClassLayout.parseInstance(a).toPrintable());
                }
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
//        t2.join();
        TimeUnit.SECONDS.sleep(5);
        Thread t3 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    out.println("t3 lock " + ClassLayout.parseInstance(a).toPrintable());
                }
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t3.start();
//        t3.join();
//        TimeUnit.SECONDS.sleep(5);
    }

}