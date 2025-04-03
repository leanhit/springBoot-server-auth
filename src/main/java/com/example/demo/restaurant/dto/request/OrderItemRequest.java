package com.example.demo.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.OrderItem}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    private UUID itemId;

    private Long quantity;

    private String specifications;

}
