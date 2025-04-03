package com.example.demo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.restaurant.entity.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}