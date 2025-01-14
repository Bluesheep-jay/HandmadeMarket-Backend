package com.handmadeMarket.Users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.handmadeMarket.Role.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private String id;
    private String email;
    private String username;
    private String avatarUrl;
    private String phoneNumber;
    private EnumRole enumRole;
    private String rankName;
    private int rankNumber;
    private int points;
    private double rankDiscount;
    private String shopId;
    private List<String> addressIdList;
    private String cartId;
}