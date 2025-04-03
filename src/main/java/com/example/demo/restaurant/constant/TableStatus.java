package com.example.demo.restaurant.constant;

import lombok.Getter;

@Getter
public enum TableStatus {

    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE"),
    OCCUPIED("OCCUPIED");

    private final String status;

    TableStatus(String status) {
        this.status = status;
    }

}
