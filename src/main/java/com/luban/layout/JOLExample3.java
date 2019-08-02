package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

public class JOLExample3 {
    static A a;

    public static void main(String[] args) throws Exception {
        //-XX:BiasedLockingStartupDelay=0
//        Thread.sleep(5000);
        a = new A();
//        Thread.sleep(5000);
        out.println("befre lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        sync();
        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
//        hash();
    }

    public static void sync() throws InterruptedException {
        synchronized (a) {
            out.println("exec lock");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }
    }

    public static void hash() throws InterruptedException {
        a.hashCode();
        out.println("exec hashCode");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }

}