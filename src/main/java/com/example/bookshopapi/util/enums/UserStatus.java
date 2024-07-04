package com.example.bookshopapi.util.enums;

public enum UserStatus {
    ACTIVE(1),
    INACTIVE(0),
    DELETED(-1);
    private final Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
