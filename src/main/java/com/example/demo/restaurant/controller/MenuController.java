package com.example.demo.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.restaurant.dto.MenuDto;
import com.example.demo.restaurant.service.MenuService;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("")
    public MenuDto getMenu() {
        return menuService.getMenu();
    }

}
