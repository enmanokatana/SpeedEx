package org.example.server.Aichat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AiChatResponse {

    private String message;
}
