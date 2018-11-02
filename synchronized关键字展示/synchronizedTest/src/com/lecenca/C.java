package com.lecenca;

import java.io.IOException;

//因为此处synchronized以this为锁，而c1，c2是不同对象，
//所以thread01占有的锁跟main所在线程获取的锁不一样，main线程可以进入foo2。
public class C {
    public synchronized void foo1(){
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
        final C c1 = new C();
        C c2 = new C();
        Thread thread01 = new Thread(new Runnable() {
            @Override
            public void run() {
                c1.foo1();
            }
        });

        thread01.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c2.foo2();
    }
}
