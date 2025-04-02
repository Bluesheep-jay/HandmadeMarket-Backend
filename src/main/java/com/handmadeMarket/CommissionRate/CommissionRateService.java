package com.handmadeMarket.CommissionRate;

import com.handmadeMarket.Order.dto.MonthlyRevenueResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommissionRateService {
    private final String CANCELLED_STATUS_ID = "67dbfbaa09072401a615967d";

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



//    public double getCommissionRate(double revenue) {
//        Query query = new Query(new Criteria()
//                .andOperator(
//                        Criteria.where("min_price").lte(revenue),
//                        Criteria.where("max_price").gt(revenue)
//                )
//        );
//
//        CommissionRate commissionRate = mongoTemplate.findOne(query, CommissionRate.class);
//        return commissionRate != null ? commissionRate.getCommissionRate() : 0.0;
//    }


    public List<MonthlyRevenueResult> getMonthlyCommissionForYear(String shopId, int year) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("order_shop_id").is(shopId)
                .and("order_status_id").ne(CANCELLED_STATUS_ID)
                .and("order_date").ne(null)
                .andOperator(
                        Criteria.where("order_date").gte(Instant.parse(String.format("%d-01-01T00:00:00.000Z", year)))
                                .lt(Instant.parse(String.format("%d-01-01T00:00:00.000Z", year + 1)))
                )
        );

        UnwindOperation unwindOperation = Aggregation.unwind("order_details");

        ProjectionOperation projectStage = Aggregation.project()
                .and(DateOperators.dateOf("order_date").toString("%Y-%m")).as("month")
                .and("order_details.price").as("price")
                .and("order_details.quantity").as("quantity");

        GroupOperation groupStage = Aggregation.group("month")
                .sum(ArithmeticOperators.Multiply.valueOf("price").multiplyBy("quantity")).as("totalRevenue");

        SortOperation sortStage = Aggregation.sort(Sort.by(Sort.Direction.ASC, "_id"));

        Aggregation aggregation = Aggregation.newAggregation(matchStage, unwindOperation, projectStage, groupStage, sortStage);

        AggregationResults<MonthlyRevenueResult> result = mongoTemplate.aggregate(aggregation, "order", MonthlyRevenueResult.class);

        List<MonthlyRevenueResult> revenueResults = result.getMappedResults();

        // Tính hoa hồng dựa trên doanh thu
        for (MonthlyRevenueResult revenue : revenueResults) {
            double commissionRate = getCommissionRate(revenue.getTotalRevenue());
            revenue.setTotalRevenue(revenue.getTotalRevenue() * commissionRate);
        }

        return revenueResults;
    }

    public double getCommissionRate(double revenue) {
        List<CommissionRate> commissionRates = mongoTemplate.findAll(CommissionRate.class);
        for (CommissionRate rate : commissionRates) {
            if (revenue >= rate.getMinPrice() && revenue < rate.getMaxPrice()) {
                return rate.getCommissionRate();
            }
        }
        return 0.0; // Trả về 0 nếu không có mức phù hợp
    }

}
