package com.lcy.demo.mutilthread;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @description:
 * @author: luchenyang
 * @date: 2024/2/23
 */
public class SynUse {
    public static void main(String[] args) throws InterruptedException {
        //输出当前虚拟机的信息
        System.out.println(VM.current().details());
//        System.out.println(VM.current().);
        //输出类A 的内存布局
        Object synUse = new Object();
        synUse.hashCode();
        System.out.println(ClassLayout.parseInstance(synUse).toPrintable());
        Thread.sleep(5000);
        System.out.println(ClassLayout.parseInstance(synUse).toPrintable());
        synchronized (synUse){
            System.out.println(ClassLayout.parseInstance(synUse).toPrintable());
        }
    }
}