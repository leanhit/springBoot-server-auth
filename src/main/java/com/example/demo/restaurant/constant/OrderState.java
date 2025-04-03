package com.example.demo.restaurant.constant;

import lombok.Getter;

@Getter
public enum OrderState {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    PAID("PAID"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    private final String state;

    OrderState(String state) {
        this.state = state;
    }

}
