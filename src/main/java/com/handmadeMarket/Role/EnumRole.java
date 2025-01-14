package com.handmadeMarket.Role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumRole {
    ADMIN,
    SHOP,
    USER;

    @JsonCreator
    public static EnumRole fromValue(String value) {
        for (EnumRole role : values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
