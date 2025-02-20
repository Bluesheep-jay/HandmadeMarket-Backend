package com.handmadeMarket.Voucher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voucher {
    private String id;

    @Field("voucher_name")
    private String voucherName;

    @Field("voucher_code")
    private String voucherCode;

    @Field("voucher_discount")
    private double voucherDiscount;

    @Field("voucher_status")
    private String voucherStatus;

    @Field("voucher_create_date")
    private String voucherCreateDate;

    @Field("voucher_expired_date")
    private String voucherExpiredDate;

}
