package com.zgczx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        log.info("【websocket】 有新的连接，总数={}",webSockets.size());
    }

    @OnClose
    public void OnClose(){
        webSockets.remove(this);
        log.info("【websocket】 断开连接，总数={}",webSockets.size());
    }

    @OnMessage
    public void OnMessage(String message){
        log.info("【websocket】 收到客户端发来的消息，消息={}",message);
    }

    public void sendMessage(String message){
        for(WebSocket webSocket:webSockets){
            log.info("【websocket】 广播消息，小心={}",message);
            try{
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
