package com.handmadeMarket.Users;

import com.handmadeMarket.Role.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;

    @Field("avatar_url")
    private String avatarUrl;
    @Field("phone_number")
    private String phoneNumber;

    @Field("enum_role")
    private EnumRole enumRole;

    @Field("rank_number")
    private int rankNumber;
    @Field("rank_name")
    private String rankName;

    private int points;
    @Field("rank_discount")
    private double rankDiscount;

    @Field("shop_id")
    private String shopId;

    @Field("address_id_list")
    private List<String> addressIdList = new ArrayList<>();

    @Field("cart_id")
    private String cartId;
}
