package com.example.demo.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.restaurant.constant.OrderState;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Order}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {
    private UUID id;
    private Set<OrderItemDto> items = new LinkedHashSet<>();
    private UUID tableId;
    private OrderState state;
    private Instant createdDate;
    private Instant lastModifiedDate;
}