package com.handmadeMarket.CommissionRate;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommissionRateService {
    private final CommissionRateRepository commissionRateRepository;
    private final MongoTemplate mongoTemplate;

    public CommissionRateService(CommissionRateRepository commissionRateRepository,
                                 MongoTemplate mongoTemplate) {
        this.commissionRateRepository = commissionRateRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<CommissionRate> getAll() {
        return commissionRateRepository.findAll();
    }

    public CommissionRate create(CommissionRate commissionRate) {
        return commissionRateRepository.save(commissionRate);
    }



    public double getCommissionRate(double revenue) {
        Query query = new Query(new Criteria()
                .andOperator(
                        Criteria.where("min_price").lte(revenue),
                        Criteria.where("max_price").gt(revenue)
                )
        );

        CommissionRate commissionRate = mongoTemplate.findOne(query, CommissionRate.class);
        return commissionRate != null ? commissionRate.getCommissionRate() : 0.0;
    }
}
