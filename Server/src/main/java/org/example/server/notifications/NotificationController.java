package org.example.server.notifications;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.UserDto;
import org.example.server.enums.Action;
import org.example.server.models.ExamGroup;
import org.example.server.models.User;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.Instant;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final MemberStore memberStore;
    private final SimpMessagingTemplate template;


    @MessageMapping("/user")
    public void getUsers(User user, SimpMessageHeaderAccessor headerAccessor)throws Exception{
        // TODO do rhis hahhahah
    }

    @EventListener
    public void handleSessionConnectEvent(SessionConnectEvent event)
    {
        System.out.println("Session connect Event");
    }
    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event)
    {
        System.out.println("Session Disconnect Event");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String,Object> sessionAttributs = headerAccessor.getSessionAttributes();
        if (sessionAttributs == null){
            return;
        }
        User user = (User) sessionAttributs.get("user");
        if (user == null){
            return;
        }
        memberStore.RemoveUser(user.getId());
    }
    @MessageMapping("/sendNotification") // Endpoint to receive messages
    @SendTo("/topic/notifications") // Broadcasts the message to clients subscribed to "/topic/notifications"
    public Notification sendNotification(String notification) {
        System.out.println();
        return new Notification("test", UserDto.builder().build(), Action.Update, Instant.now());
    }
}

