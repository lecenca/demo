package com.lecenca;

import java.io.IOException;

//B的foo1和foo2都被synchronized修饰，thread01执行foo1占有锁时，main所在线程无法进入foo2。
//因为这里synchronize都以this为锁。
public class B {
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
        final B b = new B();
        Thread thread01 = new Thread(new Runnable() {
            @Override
            public void run() {
                b.foo1();
            }
        });

        thread01.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        b.foo2();
    }
}
