package com.example.demo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.restaurant.constant.OrderState;
import com.example.demo.restaurant.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByTableId(UUID tableId);

    Optional<Order> findFirstByTableIdOrderByCreatedDateAsc(UUID tableId);

    Optional<Order> findFirstByTableIdAndStateOrderByCreatedDateDesc(UUID tableId, OrderState state);

}