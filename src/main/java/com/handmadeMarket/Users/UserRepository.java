package com.handmadeMarket.Users;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<Users, String> {
    boolean existsByEmail(String email);

    Users findByEmail(String email);

    long countUsersByRankId(String rankId);
}
