package com.luban.layout;
import org.openjdk.jol.info.ClassLayout;
import static java.lang.System.out;

public class JOLExample5 {
    static A a;
    public static void main(String[] args) throws Exception {
        //Thread.sleep(5000);
        a = new A();
        out.println("befre lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());

        Thread t1= new Thread(){
            public void run() {
                synchronized (a){
                    try {
                        Thread.sleep(5000);
                        System.out.println("t1 release");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        Thread.sleep(1000);
        out.println("t1 lock ing");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        sync();
        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());

        System.gc();
        out.println("after gc()");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }

    public static void sync() throws InterruptedException {
        synchronized (a){
            System.out.println("main lock");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        out.println("main end");
    }
}