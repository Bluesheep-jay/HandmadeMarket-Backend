package com.handmadeMarket.Chat;

import com.handmadeMarket.Chat.dto.ChatPartner;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    private final ChatMessageService chatMessageService;

    public ChatRestController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory(
            @RequestParam String user1,
            @RequestParam String user2
    ) {
        return chatMessageService.getChatHistory(user1, user2);
    }

    @GetMapping("/partners/{userId}")
    public List<ChatPartner> getChatPartners(@PathVariable String userId) {
        return chatMessageService.findChatPartnersWithDetails(userId);
    }
}
