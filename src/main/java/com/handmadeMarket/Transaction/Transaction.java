package com.handmadeMarket.Transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private String id;

    @Field("transaction_no")
    private String transactionNo;

    @Field("transaction_amount")
    private BigDecimal transactionAmount;

    @Field("card_type")
    private String cardType;

    @Field("transaction_bank_code")
    private String transactionBankCode;

    @Field("transaction_create_at")
    private Instant transactionCreatedAt;

    @Field("order_id")
    private String orderId;

    @Field("transaction_user_id")
    private String transactionUserId;


    @Field("transaction_shop_id")
    private String transactionShopId;


}
