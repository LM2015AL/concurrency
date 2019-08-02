package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

public class JOLExample10 {
    static A a;
    public static void main(String[] args) throws Exception {
        //Thread.sleep(5000);
        a = new A();
        out.println("befre lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());

        Thread t1= new Thread(){
            public void run() {
                synchronized (a){
                    System.out.println("t1 lock");
                    out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        t1.start();
        t1.join();
        synchronized (a){
            System.out.println("main lock");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }

        Thread t2= new Thread(){
            public void run() {
                synchronized (a){
                    System.out.println("t2 lock");
                    out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        t2.start();
    }

}