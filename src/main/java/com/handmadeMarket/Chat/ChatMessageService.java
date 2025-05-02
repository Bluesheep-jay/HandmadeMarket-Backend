package com.handmadeMarket.Chat;

import com.handmadeMarket.Chat.dto.ChatPartner;
import com.handmadeMarket.Users.UserRepository;
import com.handmadeMarket.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository usersRepository;

    @Autowired
    public ChatMessageService(ChatMessageRepository chatMessageRepository,
                              UserRepository usersRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.usersRepository = usersRepository;
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage>  getMessagesBetween(String user1, String user2) {
        return chatMessageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                user1, user2, user1, user2
        );
    }

    public List<ChatMessage> getChatHistory(String user1, String user2) {
        return chatMessageRepository.findBySenderAndReceiver(user1, user2);
    }

    public List<ChatPartner> findChatPartnersWithDetails(String userId) {
        List<ChatMessage> messages = chatMessageRepository.findAllMessagesByUser(userId);

        Set<String> partnerIds = new HashSet<>();
        for (ChatMessage msg : messages) {
            if (!msg.getSenderId().equals(userId)) {
                partnerIds.add(msg.getSenderId());
            }
            if (!msg.getReceiverId().equals(userId)) {
                partnerIds.add(msg.getReceiverId());
            }
        }

        List<Users> partners = usersRepository.findAllById(partnerIds);

        return partners.stream()
                .map(user -> new ChatPartner(user.getId(), user.getUsername(), user.getAvatarUrl()))
                .collect(Collectors.toList());
    }

}