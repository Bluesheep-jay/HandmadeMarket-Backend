package com.handmadeMarket.ShopRevenue;

import com.handmadeMarket.CommissionRate.CommissionRate;
import com.handmadeMarket.CommissionRateHistory.CommissionRateHistory;
import com.handmadeMarket.Exception.ResourceNotFoundException;
import com.handmadeMarket.Order.Order;
import com.handmadeMarket.Order.OrderRepository;
import com.handmadeMarket.Order.dto.MonthlyRevenueResult;
import com.handmadeMarket.ShopRevenue.dto.MonthlyShopRevenue;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ShopRevenueService {
    private final String CANCELLED_STATUS_ID = "67dbfbaa09072401a615967d";

    private final MongoTemplate mongoTemplate;
    private final ShopRevenueRepository shopRevenueRepository;
    @Autowired
    private OrderRepository orderRepository;

    public ShopRevenueService(ShopRevenueRepository shopRevenueRepository,
                              MongoTemplate mongoTemplate
                              ) {
        this.shopRevenueRepository = shopRevenueRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<ShopRevenue> getAll() {
        return shopRevenueRepository.findAll();
    }

    public ShopRevenue create(ShopRevenue shopRevenue) {
        return shopRevenueRepository.save(shopRevenue);
    }

//    @Scheduled(cron = "0 0 0 1 * ?")

    @Scheduled(fixedRate = 600000)
    public void updateShopRevenue() {
        List<String> shopIds = getAllShopIds();
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        System.out.println("shopRevenueService/67");

        for (String shopId : shopIds) {
            MonthlyShopRevenue monthlyRevenue = getTotalRevenueForMonth(shopId, (currentMonth -1), currentYear);
            if ( monthlyRevenue != null) {
               ShopRevenue shopRevenue = new ShopRevenue();
            shopRevenue.setShopId(shopId);
            shopRevenue.setMonth(monthlyRevenue.getMonth());
            shopRevenue.setTotalRevenue(monthlyRevenue.getTotalRevenue());
            shopRevenue.setCommissionRate(monthlyRevenue.getCommissionRate());
            shopRevenue.setCommissionFee(monthlyRevenue.getCommissionFee());
            mongoTemplate.save(shopRevenue);
            }
        }
    }



    public MonthlyShopRevenue getTotalRevenueForMonth(String shopId, int month, int year) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("order_shop_id").is(shopId)
                .and("order_status_id").ne(CANCELLED_STATUS_ID)
                .and("order_date").ne(null)
                .andOperator(
                        Criteria.where("order_date").gte(Instant.parse(String.format("%d-%02d-01T00:00:00.000Z", year, month)))
                                .lt(Instant.parse(String.format("%d-%02d-01T00:00:00.000Z", year, month + 1)))
                )
        );

        UnwindOperation unwindOperation = Aggregation.unwind("order_details");

        ProjectionOperation projectStage = Aggregation.project()
                .and(DateOperators.dateOf("order_date").toString("%Y-%m")).as("month")
                .and("order_details.price").as("price")
                .and("order_details.quantity").as("quantity");

        GroupOperation groupStage = Aggregation.group("month")
                .sum(ArithmeticOperators.Multiply.valueOf("price").multiplyBy("quantity")).as("totalRevenue");

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

        CommissionRateHistory history = mongoTemplate.findOne(
                Query.query(Criteria.where("start_date").lte(Instant.parse(month + "-01T00:00:00.000Z"))
                        .orOperator(Criteria.where("end_date").is(null), Criteria.where("end_date").gt(Instant.parse(month + "-01T00:00:00.000Z")))),
                CommissionRateHistory.class);

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
         return mongoTemplate.findDistinct( "order_shop_id", Order.class, String.class);
    }
}