package com.example.demo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.restaurant.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}