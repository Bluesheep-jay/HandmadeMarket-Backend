package com.handmadeMarket.Rank;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RankRepository extends MongoRepository<Rank, String> {
    Rank findByRankNumber(int rankNumber);
}
