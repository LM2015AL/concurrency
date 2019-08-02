package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

/*主线程ID，怎么重复，所个几把*/
public class JOLExample13 {

    static A a;
    public static void main(String[] args) throws Exception {
        a = new A();
        synchronized (a) {
            out.println("main lock " + ClassLayout.parseInstance(a).toPrintable());
        }

        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    out.println("t1 lock " + ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        t1.start();
        t1.join();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    out.println("t2 lock " + ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        t2.start();
        t2.join();
        Thread t3 = new Thread() {
            @Override
            public void run() {
                synchronized (a) {
                    out.println("t3 lock " + ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        t3.start();
        t3.join();
    }

}