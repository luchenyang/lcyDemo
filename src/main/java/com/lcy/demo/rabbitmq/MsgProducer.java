package com.lcy.demo.rabbitmq;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MsgProducer {

    private static String key = "1234567890ABCDEF";
    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;
    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMrpMessage(String content,String routingkey) {
        CorrelationData correlationId = new CorrelationData(IdUtil.randomUUID());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMqConfig.MRP_EXCHANGE, routingkey, content, correlationId);
    }

    public void sendMerMessage(String content,String routingkey) {
        CorrelationData correlationId = new CorrelationData(IdUtil.randomUUID());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMqConfig.MER_EXCHANGE, routingkey, content, correlationId);
    }

    public void sendAgentMessage(String content,String routingkey) {
        CorrelationData correlationId = new CorrelationData(IdUtil.randomUUID());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMqConfig.AGENT_EXCHANGE, routingkey, content, correlationId);
    }

    public void sendOmsMessage(String content,String routingkey) {
        CorrelationData correlationId = new CorrelationData(IdUtil.randomUUID());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitMqConfig.OMS_EXCHANGE, routingkey, content, correlationId);
    }

}
