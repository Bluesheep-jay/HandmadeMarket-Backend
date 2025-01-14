package com.handmadeMarket.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus {
    @Id
    private String id;

    @Field("status_name")
    private String statusName;

    @Field("status_description")
    private String statusDescription;
}
