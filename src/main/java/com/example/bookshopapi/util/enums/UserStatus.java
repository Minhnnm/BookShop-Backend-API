package com.example.bookshopapi.util.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
    ACTIVE(1, "Tài khoản khách hàng đã hoạt động trở lại"),
    INACTIVE(0, "Tài khoản khách hàng đã bị khóa/không hoạt động"),
    DELETED(-1, "Tài khoản khách hàng đã được xóa");
    private final Integer value;
    private final String message;

    UserStatus(Integer value, String message) {
        this.value = value;
        this.message=message;
    }

    public Integer getValue() {
        return this.value;
    }
    public String getMessage(){
        return this.message;
    }
    private static final Map<Integer, UserStatus> statusMessages = new HashMap<>();

    static {
        for (UserStatus status : UserStatus.values()) {
            statusMessages.put(status.getValue(), status);
        }
    }

    public static String getMessageByValue(int value) {
        UserStatus status = statusMessages.get(value);
        return status != null ? status.getMessage() : "Trạng thái tài khoản không xác định!";
    }
}
