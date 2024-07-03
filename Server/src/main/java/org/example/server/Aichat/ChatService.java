package org.example.server.Aichat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service

public class ChatService implements ChatServiceInterface{


    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public AiChatResponse generateMessage(String prompt) {
        return  new AiChatResponse().setMessage( chatClient.prompt()
                .user("hello")
                .call()
                .content());
    }

    @Override
    public AiChatResponse generateJoke(String topic) {
        return null;
    }
}
