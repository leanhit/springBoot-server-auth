package com.example.demo.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.demo.restaurant.constant.CategoryAvailableStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Category}
 */
@Data
@AllArgsConstructor
public class CreateCategoryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private CategoryAvailableStatus availableStatus;

}
