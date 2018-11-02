package com.lecenca;

import java.io.IOException;

//因为两处synchronized请求的锁不一样，所以main线程仍然可以进入foo2
public class E {
    public static synchronized void foo1(){
        System.out.println("foo1 001");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //此处一直占有锁
        System.out.println("foo1 002");
    }

    public synchronized void foo2(){
        System.out.println("foo2 001");
        System.out.println("foo2 002");
    }

    public static void main(String[] args) {
        E ee = new E();

        Thread thread01 = new Thread(new Runnable() {
            @Override
            public void run() {
                E.foo1();
            }
        });

        thread01.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ee.foo2();
    }

}
