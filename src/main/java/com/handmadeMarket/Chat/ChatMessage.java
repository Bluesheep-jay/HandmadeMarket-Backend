package com.handmadeMarket.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ChatMessage {
    private String senderId;
    private String receiverId;
    private String content;
    private String timestamp;
}