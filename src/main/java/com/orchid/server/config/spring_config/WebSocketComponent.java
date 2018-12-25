package com.orchid.server.config.spring_config;

import com.google.gson.Gson;
import com.orchid.server.domain.SocketMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketComponent {

    @Autowired
    private SimpMessagingTemplate simpleMsgTemplate;

    private Gson gson = new Gson();

    public void broadcastMessageES(SocketMsg msg) {
        String msgStr = gson.toJson(msg);
        this.simpleMsgTemplate.convertAndSend("/orchidWSChannel", msgStr);
        System.out.println("***********************************************************************");
        System.out.println("WS Message sent out: " + msg.getMessage());
        System.out.println("***********************************************************************");
    }

    @MessageMapping("/send/genericMessageWS")
    public SocketMsg onGenericMessageReceived(String message) {
        System.out.println("***********************************************************************");
        System.out.println("WS Received Message : " + message);
        System.out.println("***********************************************************************");
        return gson.fromJson(message, SocketMsg.class);
    }
}
