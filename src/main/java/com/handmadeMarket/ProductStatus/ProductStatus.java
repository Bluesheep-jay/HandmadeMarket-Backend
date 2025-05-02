package com.handmadeMarket.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatus {
    @Id
    private String id;
    @Field("status_code")
    private String statusCode;
    @Field("status_name")
    private String statusName;
    @Field("status_description")
    private String statusDescription;
}
