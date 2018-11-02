package com.lecenca;

import java.io.IOException;

//此处synchronize都以D的class对象为锁，所以foo2的访问被阻塞。
public class D {
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

    public static synchronized void foo2(){
        System.out.println("foo2 001");
        System.out.println("foo2 002");
    }

    public static void main(String[] args) {
        Thread thread01 = new Thread(new Runnable() {
            @Override
            public void run() {
                D.foo1();
            }
        });

        thread01.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        D.foo2();
    }
}
