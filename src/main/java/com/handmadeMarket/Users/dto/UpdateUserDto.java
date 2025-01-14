package com.handmadeMarket.Users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDto {
    private String username;
    private String password;
    private String email;
    private String avatarUrl;
    private String enumRole;
    private String phoneNumber;

    private String rankName;
    private int rankNumber;
    private int points;
    private double rankDiscount;
}
