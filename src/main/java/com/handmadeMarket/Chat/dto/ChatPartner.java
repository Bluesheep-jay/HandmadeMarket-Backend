package com.handmadeMarket.Chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatPartner {
    private String id;
    private String username;
    private String avatarUrl;
}

