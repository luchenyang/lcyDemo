package com.lcy.demo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Slf4j
public class RabbitMqConfig implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;


    public static final String MRP_EXCHANGE = "mrp-direct-exchange";
    public static final String MER_EXCHANGE = "mer-direct-exchange";
    public static final String OMS_EXCHANGE = "pospweb-direct-exchange";
    public static final String AGENT_EXCHANGE = "posx-direct-exchange";


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
        return template;
    }

    @Bean
    public DirectExchange merExchange() {
        return new DirectExchange(MER_EXCHANGE);
    }

    @Bean
    public Queue queueA() {
        return new Queue("QUEUE_A", true); //队列持久
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(merExchange()).with("xxxx");
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
            log.info("correlationData,{}",correlationData);
            log.info("boolean,{}",b);
            log.info("content,{}",s);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("message,{}",message);
        log.info("i,{}",i);
        log.info("s,{}",s);
        log.info("s1,{}",s1);
        log.info("s2,{}",s2);
    }


}
