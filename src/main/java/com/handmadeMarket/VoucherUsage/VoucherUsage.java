package com.handmadeMarket.VoucherUsage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "voucher_usages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherUsage {
    @Id
    private String id;

    @Field("voucher_id")
    private String voucherId;

    @Field("user_id")
    private String userId;

    @Field("order_id")
    private String orderId;

    @Field("used_at")
    private Instant usedAt;
}