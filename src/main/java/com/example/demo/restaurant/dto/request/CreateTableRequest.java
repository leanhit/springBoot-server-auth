package com.example.demo.restaurant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link com.example.demo.restaurant.entity.TableEntity}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTableRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private Integer maxSeat;

}
