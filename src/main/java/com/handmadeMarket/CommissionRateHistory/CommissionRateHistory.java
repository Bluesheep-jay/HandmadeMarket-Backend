package com.handmadeMarket.CommissionRateHistory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handmadeMarket.CommissionRate.CommissionRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionRateHistory {
    @Id
    private String id;

    @Field("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant startDate;

    @Field("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant endDate;

    @Field("commission_rates")
    private List<CommissionRate> commissionRates;
}