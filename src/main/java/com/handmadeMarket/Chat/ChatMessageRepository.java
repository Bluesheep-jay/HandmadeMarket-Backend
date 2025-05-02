package com.handmadeMarket.Chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(String user1, String user2, String user11, String user21);

    @Query("{ $or: [ " +
            "{ senderId: ?0, receiverId: ?1 }, " +
            "{ senderId: ?1, receiverId: ?0 } " +
            "] }")
    List<ChatMessage> findBySenderAndReceiver(String user1, String user2);


    @Query("{ '$or': [ { 'senderId': ?0 }, { 'receiverId': ?0 } ] }")
    List<ChatMessage> findAllMessagesByUser(String userId);

}
