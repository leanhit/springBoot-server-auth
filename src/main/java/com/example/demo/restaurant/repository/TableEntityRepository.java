package com.example.demo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.restaurant.entity.TableEntity;

import java.util.UUID;

public interface TableEntityRepository extends JpaRepository<TableEntity, UUID> {
}