package com.example.demo.restaurant.dto.request;

import lombok.Data;

import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Order}
 */
@Data
public class CreateOrderRequest {

    private UUID tableId;

}
