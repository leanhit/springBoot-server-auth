package com.example.demo.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.demo.restaurant.constant.TableStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.TableEntity}
 */
@Data
@AllArgsConstructor
public class TableDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String description;
    private Integer maxSeat;
    private TableStatus status;
}