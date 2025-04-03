package com.example.demo.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.restaurant.constant.CategoryAvailableStatus;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto implements Serializable {
    private UUID id;
    private String name;
    private CategoryAvailableStatus availableStatus;
    private transient List<ItemDto> items;
}