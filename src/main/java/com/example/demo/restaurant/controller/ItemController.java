package com.example.demo.restaurant.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.demo.restaurant.dto.ItemDto;
import com.example.demo.restaurant.dto.response.CollectionResponse;
import com.example.demo.restaurant.service.ItemService;
import com.example.demo.restaurant.support.PageUtils;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("")
    @PageableAsQueryParam
    public CollectionResponse<ItemDto> getAll(@Parameter(hidden = true) Pageable pageable) {
        Page<ItemDto> items = itemService.findAllItems(pageable);

        return CollectionResponse.of(items.getContent(), PageUtils.asPageMetadata(items));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto) {
        ItemDto item = itemService.createItem(itemDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(item);
    }

    @GetMapping("/{id}")
    public ItemDto getItem(@PathVariable UUID id) {
        return itemService.findById(id);
    }

    @PutMapping("/{id}")
    public ItemDto updateItem(@PathVariable UUID id, @RequestBody ItemDto itemDto) {
        itemDto.setId(id);
        return itemService.updateItem(itemDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable UUID id) {
        itemService.deleteItem(id);
    }

}
