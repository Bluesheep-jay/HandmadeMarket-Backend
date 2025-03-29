package com.handmadeMarket.Revenue;

import com.handmadeMarket.CommissionRate.CommissionRateService;
import com.handmadeMarket.Order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class RevenueService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommissionRateService commissionRateService;

    public void updateMonthlyRevenue(Order order) {
        String shopId = order.getOrderShopId();
        LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().toEpochMilli())
                .atZone(ZoneId.of("UTC")).toLocalDate();

        int year = orderDate.getYear();
        int month = orderDate.getMonthValue();

        // Tính doanh thu từ orderDetails
        double revenueFromOrder = order.getOrderDetails().stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getQuantity())
                .sum();

        // Cập nhật doanh thu của shop
        Query query = new Query(Criteria.where("shop_id").is(shopId).and("year").is(year).and("month").is(month));
        Update update = new Update().inc("total_revenue", revenueFromOrder);
        mongoTemplate.upsert(query, update, Revenue.class);

        // Cập nhật commission rate
        updateCommissionRate(shopId, year, month);
    }

    private void updateCommissionRate(String shopId, int year, int month) {
        Query query = new Query(Criteria.where("shop_id").is(shopId).and("year").is(year).and("month").is(month));
        Revenue revenue = mongoTemplate.findOne(query, Revenue.class);

        if (revenue != null) {
            double commissionRate = commissionRateService.getCommissionRate(revenue.getTotalRevenue());
            Update update = new Update().set("commission_rate", commissionRate);
            mongoTemplate.updateFirst(query, update, Revenue.class);
        }
    }
}

