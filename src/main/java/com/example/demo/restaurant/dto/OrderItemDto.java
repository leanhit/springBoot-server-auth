package com.example.demo.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.restaurant.constant.OrderItemState;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.OrderItem}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto implements Serializable {
    private UUID id;
    private UUID itemId;
    private Long quantity;
    private Double price;
    private String specifications;
    private OrderItemState state;
    private UUID orderTableId;
    private Instant createdDate;
    private Instant lastModifiedDate;
}