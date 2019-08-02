package com.luban.layout;

public class B {
//    boolean a = true;
    int i;

    public B() {
    }

    public B(int i) {
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

