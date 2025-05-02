package com.handmadeMarket.Voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "vouchers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("discount_value")
    private double discountValue;

    @Field("min_order_value")
    private double minOrderValue;

    @Field("usage_limit")
    private int usageLimit;

    @Field("used_count")
    private int usedCount;

    @Field("start_date")
    private Instant startDate;

    @Field("end_date")
    private Instant endDate;

    @Field("creator_type")
    private String creatorType; //ADMIN, SHOP

    @Field("creator_id")
    private String creatorId;

    @Field("shop_id")
    private String shopId;

    @Field("status")
    private String status;

    @Field("created_at")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant createdAt;
}