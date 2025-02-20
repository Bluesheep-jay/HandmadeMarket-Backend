package com.handmadeMarket.Transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;

    @Field("transaction_no")
    private String transactionNo;

    @Field("transaction_amount")
    private String transactionAmount;

    @Field("card_type")
    private String cardType;

    @Field("transaction_bank_code")
    private Date transactionBankCode;

    @Field("order_id")
    private String orderId;

    @Field("transaction_status")
    private Date transactionStatus;

    @Field("transaction_create_at")
    private Date transactionCreatedAt;
}
