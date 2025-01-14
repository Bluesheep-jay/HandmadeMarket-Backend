package com.handmadeMarket.PaymentMethod;

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
public class PaymentMethod {
    @Id
    private String id;

    @Field("method_name")
    private String methodName;

    @Field("method_is_active")
    private boolean methodIsActive;
}
