package com.lecenca;

import java.io.IOException;

//A的foo1成员方法被标注synchronized，不会影响未被synchronized修饰的foo2的访问。
public class A {

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

    public void foo2(){
        System.out.println("foo2 001");
        System.out.println("foo2 002");
    }

    public static void main(String[] args) {
        final A a = new A();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                a.foo1();
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a.foo2();
    }
}
