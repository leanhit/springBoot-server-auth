package com.example.demo.restaurant.constant;

import lombok.Getter;

@Getter
public enum ItemAvailableStatus {

    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE"),
    UNAVAILABLETODAY("UNAVAILABLETODAY"),
    HIDE("HIDE");

    private final String status;

    ItemAvailableStatus(String status) {
        this.status = status;
    }

}
