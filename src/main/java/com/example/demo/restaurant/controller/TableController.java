package com.example.demo.restaurant.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.demo.restaurant.dto.OrderDto;
import com.example.demo.restaurant.dto.TableDto;
import com.example.demo.restaurant.dto.request.CreateTableRequest;
import com.example.demo.restaurant.dto.response.CollectionResponse;
import com.example.demo.restaurant.service.TableService;
import com.example.demo.restaurant.support.PageUtils;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping("")
    @PageableAsQueryParam
    public ResponseEntity<CollectionResponse<TableDto>> getTables(@Parameter(hidden = true) Pageable pageable) {
        Page<TableDto> tables = tableService.findAllTables(pageable);
        return ResponseEntity.ok(CollectionResponse.of(tables.getContent(), PageUtils.asPageMetadata(tables)));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TableDto> createTable(@RequestBody CreateTableRequest request) {
        TableDto table = tableService.createTable(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(table.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.toString()).body(table);
    }

    @GetMapping("/{id}")
    public TableDto getTable(@PathVariable UUID id) {
        return tableService.findById(id);
    }

    @PutMapping("/{id}")
    public TableDto updateTable(@PathVariable UUID id, @RequestBody TableDto tableDto) {
        tableDto.setId(id);
        return tableService.updateTable(tableDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(@PathVariable UUID id) {
        tableService.deleteTable(id);
    }


}
