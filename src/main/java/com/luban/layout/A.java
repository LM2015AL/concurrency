package com.luban.layout;

public class A {
//    boolean a = true;
    int i;

    public A() {
    }

    public A(int i) {
        this.i = i;
    }

    public synchronized void parse1(){
        i++;
    }
    public void parse0(){
        i++;
    }
    public synchronized void parse2(){
        i++;
        JOLExample4.countDownLatch.countDown();
    }
}

