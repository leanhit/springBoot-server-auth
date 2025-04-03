package com.example.demo.restaurant.constant;

import lombok.Getter;

@Getter
public enum OrderItemState {

    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DONE("DONE"),
    FINISHED("FINISHED"),
    REJECTED("REJECTED"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    private final String state;

    OrderItemState(String state) {
        this.state = state;
    }

}
