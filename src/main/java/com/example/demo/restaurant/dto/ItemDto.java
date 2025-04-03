package com.example.demo.restaurant.dto;

import lombok.*;
import com.example.demo.restaurant.constant.ItemAvailableStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Item}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto implements Serializable {
    private UUID id;
    private String name;
    private ItemAvailableStatus availableStatus;
    private String description;
    private Double price;
    private transient List<String> photos;
    private Integer maxStock;
}