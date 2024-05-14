package com.lcy.demo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@Configuration
@Slf4j
public class RabbitMqConfig  {


    public static final String MRP_EXCHANGE = "mrp-direct-exchange";
    public static final String MER_EXCHANGE = "mer-direct-exchange";
    public static final String OMS_EXCHANGE = "pospweb-direct-exchange";
    public static final String AGENT_EXCHANGE = "posx-direct-exchange";
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);

        connectionFactory.setChannelCheckoutTimeout(1000);
        return connectionFactory;
    }

    /*
     *     简单消息监听容器
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        //同时监听多个队列
//        container.setQueues(queue001(), queue002(), queue003(), queue_image(), queue_pdf());
        //设置当前的消费者数量
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        //一个消费者一次可以从队列中拿出多少个消息
        container.setPrefetchCount(10);
        //设置是否重回队列
        container.setDefaultRequeueRejected(false);
        //设置自动签收
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //设置监听外露
        container.setExposeListenerChannel(true);
        //设置消费端标签策略
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue + "_" + UUID.randomUUID().toString();
            }
        });
//        //设置消息监听
//        container.setMessageListener(new ChannelAwareMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                String msg = new String(message.getBody(), "utf-8");
//                System.out.println("-----------消费者：" + msg);
//            }
//        });
        return container;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("correlationData,{}", correlationData);
                log.info("boolean,{}", b);
                log.info("content,{}", s);

            }
        });
        template.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.info("returnedMessage,{}", returnedMessage);
            }
        });
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






}
