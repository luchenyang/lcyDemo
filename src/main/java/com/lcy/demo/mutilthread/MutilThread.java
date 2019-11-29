package com.lcy.demo.mutilthread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class MutilThread {



    //输出ABABABABABAB
    public static void main(String[] args) {
       // lock();
        locksupport();
    }

    static  Thread B;
    static  Thread A;
    public static void locksupport(){
        A = new Thread(new Runnable() {
            @Override
            public void run() {
                        for (int i = 0; i < 7; i++) {
                            System.out.println("A");
                            LockSupport.unpark(B);
                            LockSupport.park();
                        }
            }
        },"A");
       B = new Thread(new Runnable() {
            @Override
            public void run() {
                        for (int i = 0; i < 7; i++) {
                            LockSupport.park();
                            System.out.println("B");
                            LockSupport.unpark(A);
                        }
            }
        },"B");

        A.start();
        B.start();
    }

    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Condition conditionA = reentrantLock.newCondition();
    private static Condition conditionB = reentrantLock.newCondition();
    /**
     * 可重入锁又名递归锁，是指在同一个线程在外层方法获取锁的时候，再进入该线程的内层方法会自动获取锁（前提锁对象得是同一个对象或者class），
     * 不会因为之前已经获取过还没释放而阻塞。Java中ReentrantLock和synchronized都是可重入锁，可重入锁的一个优点是可一定程度避免死锁。
     *
     *   下面的4个断点可以提现可重入锁的特性
     * */
    public static void lock(){
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();  //断点
                    for(int i =0;i<7;i++) {
                        System.out.println("A");//断点
                        conditionB.signal();
                        conditionA.await(); //断点
                        //已经释放锁了 直接进入线程B执行 最后finally 执行是为了最后一次不进入for循环  唤醒线程B 释放锁
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    conditionB.signal();
                    reentrantLock.unlock();
                }
            }

        },"A");
        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    for(int i =0;i<7;i++) {
                        System.out.println("B");//断点
                        conditionA.signal();
                        conditionB.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    conditionA.signal();
                    reentrantLock.unlock();
                }
            }
        },"B");

        A.start();
        B.start();
    }

}
