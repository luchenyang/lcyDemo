package com.lcy.demo.ws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * 描述:
 * WebScoket配置处理器
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean factoryBean = new ServletServerContainerFactoryBean();
        factoryBean.setMaxTextMessageBufferSize(10* 1024 );
        factoryBean.setMaxBinaryMessageBufferSize(10* 1024 );
        return factoryBean;
    }



    /* -------------------- start*/

    /**
     * 自动注册 websocket服务  @ServerEndpoint 注解
     */
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
    /* -------------------- end*/


    /* -------------------- start*/

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/wstest").addInterceptors(myShake()).setAllowedOriginPatterns("*");
        //SockJS 是和websokcet 一样的基础协议 有的浏览器没有websocket
        registry.addHandler(myHandler(), "/wstest").addInterceptors(myShake()).setAllowedOriginPatterns("*").withSockJS();
    }
    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }

    @Bean
    public HandShake myShake() {
        return new HandShake();
    }

    /* -------------------- end*/
}