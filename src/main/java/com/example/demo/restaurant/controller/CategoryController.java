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
import com.example.demo.restaurant.dto.CategoryDto;
import com.example.demo.restaurant.dto.request.CreateCategoryRequest;
import com.example.demo.restaurant.dto.response.CollectionResponse;
import com.example.demo.restaurant.service.CategoryService;
import com.example.demo.restaurant.support.PageUtils;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    @PageableAsQueryParam
    public ResponseEntity<CollectionResponse<CategoryDto>> getAll(@Parameter(hidden = true) Pageable pageable) {
        Page<CategoryDto> categories = categoryService.findAllCategories(pageable);

        return ResponseEntity.ok(CollectionResponse.of(categories.getContent(), PageUtils.asPageMetadata(categories)));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryDto category = categoryService.createCategory(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).body(category);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable UUID id) {
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable UUID id, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        return categoryService.updateCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDto> createItem(@PathVariable UUID id, @RequestBody List<UUID> itemIds) {
        CategoryDto category = categoryService.createItem(id, itemIds);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location).body(category);
    }

}
