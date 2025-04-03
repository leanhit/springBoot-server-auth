package com.example.demo.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.demo.restaurant.entity.Zone}
 */
@Data
@AllArgsConstructor
public class CreateZoneRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private Integer maxTable;
    private Integer maxSeat;
}
