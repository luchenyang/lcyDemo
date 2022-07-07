package com.lcy.demo.javahead;

import org.openjdk.jol.info.ClassLayout;

public class JavaHead {

        public static void main(String[] args) {
            Integer head = new Integer(1);

            System.out.println(head.hashCode());//大端小端存储
            System.out.println(Integer.toHexString(head.hashCode()));//大端小端存储

            System.out.println(ClassLayout.parseInstance(head).toPrintable());
            synchronized (head) {
                System.out.println("加锁");
            }
        }



}
