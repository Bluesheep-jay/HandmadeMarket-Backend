package com.handmadeMarket.Platform;

import com.handmadeMarket.CommissionRate.CommissionRate;
import com.handmadeMarket.CommissionRateHistory.CommissionRateHistory;
import com.handmadeMarket.Order.Order;
import com.handmadeMarket.Order.OrderRepository;
import com.handmadeMarket.Platform.dto.MonthlyShopRevenue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformRevenueService {
    private final String CANCELLED_STATUS_ID = "67dbfbaa09072401a615967d";
    private final String COMPLETED_STATUS_ID = "67d8cd2a347ab249ebe8b15b";
    private final MongoTemplate mongoTemplate;
    @Autowired
    private OrderRepository orderRepository;

    public PlatformRevenueService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public double getTotalRevenueOfPlatform(int month, int year) {
        List<String> shopIds = getAllShopIds();

        double totalRevenue = 0.0;
        for (String shopId : shopIds) {
            MonthlyShopRevenue monthlyRevenue = getTotalRevenueForMonth(shopId, month, year);
            totalRevenue += monthlyRevenue.getCommissionFee();
        }
        return totalRevenue;
    }

//    public double getMonthlyRevenueForYear(int year) {
//        List<String> shopIds = getAllShopIds();
//        double totalRevenue = 0.0;
//        for (String shopId : shopIds) {
//            for (int month = 1; month <= 12; month++) {
//                MonthlyShopRevenue monthlyRevenue = getTotalRevenueForMonth(shopId, month, year);
//                if (monthlyRevenue != null) {
//                    totalRevenue += monthlyRevenue.getCommissionFee();
//                }
//            }
//        }
//
//        return totalRevenue;
//    }

    public Map<Integer, Double> getMonthlyRevenueForYear(int year) {
        List<String> shopIds = getAllShopIds();
        Map<Integer, Double> monthlyRevenueMap = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            monthlyRevenueMap.put(month, 0.0);
        }

        for (String shopId : shopIds) {
            for (int month = 1; month <= 12; month++) {
                MonthlyShopRevenue monthlyRevenue = getTotalRevenueForMonth(shopId, month, year);
                if (monthlyRevenue != null) {
//                    System.out.println("Monthly Revenue: " + monthlyRevenue);
                    monthlyRevenueMap.compute(month, (k, current)
                            -> current + monthlyRevenue.getCommissionFee());
                }
            }
        }
        return monthlyRevenueMap;
    }

    public MonthlyShopRevenue getTotalRevenueForMonth(String shopId, int month, int year) {
        YearMonth currentMonth = YearMonth.of(year, month);
        YearMonth nextMonth = currentMonth.plusMonths(1);

        Instant start = currentMonth.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = nextMonth.atDay(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        MatchOperation matchStage = Aggregation.match(Criteria
                .where("order_shop_id").is(shopId)
                .and("order_status_id").is(COMPLETED_STATUS_ID)
                .and("order_date").ne(null)
                .andOperator(Criteria.where("order_date")
                        .gte(start)
                        .lt(end)));

        UnwindOperation unwindOperation = Aggregation.unwind("order_details");

        ProjectionOperation projectStage = Aggregation.project().and(DateOperators.dateOf("order_date").toString("%Y-%m")).as("month").and("order_details.price").as("price").and("order_details.quantity").as("quantity");

        GroupOperation groupStage = Aggregation.group("month").sum(ArithmeticOperators.Multiply.valueOf("price").multiplyBy("quantity")).as("totalRevenue");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, unwindOperation, projectStage, groupStage);

        AggregationResults<MonthlyShopRevenue> result = mongoTemplate.aggregate(aggregation, "order", MonthlyShopRevenue.class);
        MonthlyShopRevenue revenue = result.getUniqueMappedResult();
        if (revenue != null) {
            double commissionRate = getCommissionRateForMonth(revenue.getMonth(), revenue.getTotalRevenue());
            revenue.setCommissionRate(commissionRate);
            revenue.setCommissionFee(revenue.getTotalRevenue() * commissionRate);
        }
        return revenue;
    }


    private double getCommissionRateForMonth(String month, double revenue) {

        if (month == null || month.isEmpty()) {
            throw new IllegalArgumentException("Month cannot be null or empty");
        }

        CommissionRateHistory history = mongoTemplate.findOne(Query.query(Criteria.where("start_date").lte(Instant.parse(month + "-01T00:00:00.000Z")).orOperator(Criteria.where("end_date").is(null), Criteria.where("end_date").gt(Instant.parse(month + "-01T00:00:00.000Z")))), CommissionRateHistory.class);
        if (history != null) {
            for (CommissionRate rate : history.getCommissionRates()) {
                if (rate.getMinPrice() <= revenue && revenue < rate.getMaxPrice()) {
                    return rate.getCommissionRate();
                }
            }
        }
        return 0.0;
    }

    private List<String> getAllShopIds() {
        return mongoTemplate.findDistinct("order_shop_id", Order.class, String.class);
    }


}