package com.lcy.demo.mutilthread;

import java.util.concurrent.*;

/**
 * @description:
 * @author: luchenyang
 * @date: 2024/2/20
 */
public class DemoThread {

    public static void main(String[] args) {

    }

    // 1 继承Thread类
    public static class D1Thread extends Thread{
        public static void main(String[] args) {
            D1Thread d1Thread = new D1Thread();
            d1Thread.start();
        }
        @Override
        public void run(){
            System.out.println("hello D1");
        }
    }

    // 2 实现runnable接口
    public static class  D2Thread implements Runnable{
        public static void main(String[] args) {
            Thread d2Thread = new Thread(new D2Thread());
            d2Thread.start();
        }
        @Override
        public void run(){
            System.out.println("hello D2");
        }
    }

    //3 实现callable接口
    public static class D3Thread implements Callable<String>{
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            FutureTask<String> task = new FutureTask<>(new D3Thread());
            Thread d3Thread = new Thread(task);
            d3Thread.start();
            System.out.println(task.get());

        }
        @Override
        public String call() throws Exception {
            return "hello D3";
        }
    }

    //4 线程池创建
    public static  class D4Thread implements Runnable{
        public static void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(new D4Thread());
        }
        @Override
        public void run() {
            System.out.println("hello D4");
        }
    }

}