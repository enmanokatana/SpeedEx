package org.example.server.Aichat;

import org.springframework.ai.ollama.metadata.OllamaChatResponseMetadata;

public interface ChatServiceInterface {
    AiChatResponse generateMessage(String prompt);
    AiChatResponse generateJoke(String topic);
}
