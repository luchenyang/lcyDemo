package com.lcy.demo.ws;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述: spring 的版本
 * Socket处理器(包括发送信息，接收信息，信息错误等方法。)
 *
 */
@Log4j2
public class MyWebSocketHandler implements WebSocketHandler {
    /**
     * 最重要的websocket处理程序（包括发送信息，接收信息，信息错误等方法。）
     */

    /**
     * 先注册一个websocket服务器，将连接上的所有用户放进去
     */
    public static final Map<Long, WebSocketSession> USER_SOCKET_SESSION_MAP;

    static {
        USER_SOCKET_SESSION_MAP = new ConcurrentHashMap<>();
    }

    /**
     * 前台连接并且注册了账户
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");
        USER_SOCKET_SESSION_MAP.putIfAbsent(uid, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message.getPayloadLength() == 0) {
            return;
        }
        NotificationMessage msg = JSON.parseObject(message.getPayload().toString(), NotificationMessage.class);
        msg.setDate(new Date());
        log.info(JSON.toJSON(msg));
    }

    /**
     * 消息传输错误处理，如果出现错误直接断开连接
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        removeWebSocketUser(session);
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Websocket:" + session.getId() + "已经关闭");
        removeWebSocketUser(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void broadcast(final TextMessage message) throws IOException {
        // 多线程群发（给所有在线的用户发送消息）  先判断是否里面有用户（）然后循环发消息
        /*后台调用sendMessage方法的时候，前台会触发onmessage*/
        for (Entry<Long, WebSocketSession> entry : USER_SOCKET_SESSION_MAP.entrySet()) {
            if (entry.getValue().isOpen()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (entry.getValue().isOpen()) {
                                entry.getValue().sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }

    /**
     * 单个用户发消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(Long uid, TextMessage message) throws IOException {
        //根据传过来的账号，在websocketseesion的服务器里面找，接收者注册的账号
        WebSocketSession session = USER_SOCKET_SESSION_MAP.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }

    public void removeWebSocketUser(WebSocketSession session) {
        // 移除Socket会话
        for (Entry<Long, WebSocketSession> entry : USER_SOCKET_SESSION_MAP.entrySet()) {
            if (entry.getValue().getId().equals(session.getId())) {
                USER_SOCKET_SESSION_MAP.remove(entry.getKey());
                break;
            }
        }
    }
}