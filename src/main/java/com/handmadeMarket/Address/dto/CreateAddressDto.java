package com.handmadeMarket.Address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDto {
    private int provinceId;
    private int districtId;
    private String wardId;
    private String provinceName;
    private String districtName;
    private String wardName;
    private String specificAddress;
    private String recipientName;
    private String addressOfUserId;
}
