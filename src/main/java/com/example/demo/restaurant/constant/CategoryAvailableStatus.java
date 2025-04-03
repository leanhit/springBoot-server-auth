package com.example.demo.restaurant.constant;

import lombok.Getter;

@Getter
public enum CategoryAvailableStatus {

    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE"),
    HIDE("HIDE");

    private final String status;

    CategoryAvailableStatus(String status) {
        this.status = status;
    }

}
