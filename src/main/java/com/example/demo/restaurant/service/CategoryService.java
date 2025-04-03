package com.example.demo.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.restaurant.dto.CategoryDto;
import com.example.demo.restaurant.dto.request.CreateCategoryRequest;
import com.example.demo.restaurant.entity.Category;
import com.example.demo.restaurant.entity.Item;
import com.example.demo.restaurant.exception.ResourceNotFoundException;
import com.example.demo.restaurant.mapper.CategoryMapper;
import com.example.demo.restaurant.repository.CategoryRepository;
import com.example.demo.restaurant.repository.ItemRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public Page<CategoryDto> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(categoryMapper::toDto);
    }

    public CategoryDto createCategory(CreateCategoryRequest categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }

    public CategoryDto findById(UUID id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found")));
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category = categoryMapper.partialUpdate(categoryDto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryDto createItem(UUID id, List<UUID> itemIds) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
        List<Item> items = itemRepository.findAllById(itemIds);
        if (items.size() != itemIds.size()) {
            throw new ResourceNotFoundException("Some items not found");
        }
        items.forEach(item -> item.setCategory(category));
        category.getItems().addAll(items);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

}
