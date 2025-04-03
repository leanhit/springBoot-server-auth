package com.example.demo.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Zone}
 */
@Data
@AllArgsConstructor
public class ZoneDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String description;
    private Integer maxTable;
    private Integer maxSeat;
    private List<TableDto> tables;
}