package com.handmadeMarket.Users;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String> {
    boolean existsByEmail(String email);

}
