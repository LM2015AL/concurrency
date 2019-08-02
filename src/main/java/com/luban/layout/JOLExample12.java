package com.luban.layout;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class JOLExample12 {

    private static final int LIST_SIZE = 100;
    public static void main(String[] args) throws Exception {

        List<A> list = initList();

//        traversalList(list);

        Thread t1= new Thread(){

            public void run() {
                for (int i = 0; i < LIST_SIZE; i++) {
                    A a = list.get(i);
                    synchronized (a){
                        out.println("==========t1 lock i=" + i + " " + ClassLayout.parseInstance(a).toPrintable());
                    }
//                    traversalList(list);
                }
                out.println("==========t1 end\n==============================\n==============================\n==============================\n");
                try {
                    TimeUnit.SECONDS.sleep(10000);
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
//        traversalList(list);

        Thread t2= new Thread(){

            public void run() {
                for (int i = 0; i < LIST_SIZE; i++) {
                    A a = list.get(i);
                    synchronized (a){
                        out.println("==========t2 lock i=" + i + " " + ClassLayout.parseInstance(a).toPrintable());
                    }
//                    traversalList(list);
                }
                out.println("==========t2 end\n==============================\n==============================\n==============================\n");
                try {
                    TimeUnit.SECONDS.sleep(10000);
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

        Thread t3= new Thread(){

            public void run() {
                for (int i = 0; i < LIST_SIZE; i++) {
                    A a = list.get(i);
                    synchronized (a){
                        out.println("==========t3 lock i=" + i + " " + ClassLayout.parseInstance(a).toPrintable());
                    }
//                    traversalList(list);
                }
                out.println("==========t3 end\n==============================\n==============================\n==============================\n");
                try {
                    TimeUnit.SECONDS.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t3.start();
    }

    private static void traversalList(List<A> list) {
        for (int i = 1; i <= LIST_SIZE ; i++) {
            A a = list.get(i-1);
            out.println("i=" + i + " " + ClassLayout.parseInstance(a).toPrintable());
        }
    }

    private static List<A> initList() {
        List<A> list = new ArrayList<A>();
        for (int i = 1; i <= LIST_SIZE; i++) {
            list.add(new A(i));
        }
        return list;
    }

}