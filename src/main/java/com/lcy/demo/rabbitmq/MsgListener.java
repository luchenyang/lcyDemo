package com.lcy.demo.rabbitmq;


import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class MsgListener {

    // concurrency 动态拓展消费者
    // containerFactory  在RabbitMqConfig 中
    @RabbitListener(queues = "user_queue",concurrency = "5-10",containerFactory = "SimpleMessageListenerContainer")
    public void messageConsumer(String msg) throws Exception {
        System.out.println("消息：" + msg);
    }

}
