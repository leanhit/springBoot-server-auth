package com.example.demo.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.restaurant.dto.MenuDto;
import com.example.demo.restaurant.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    public MenuDto getMenu() {
        return MenuDto.builder()
                .categories(categoryService.getAll())
                .build();
    }

}
