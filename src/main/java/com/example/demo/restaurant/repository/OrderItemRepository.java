package com.example.demo.restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.restaurant.constant.OrderItemState;
import com.example.demo.restaurant.entity.OrderItem;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    Page<OrderItem> findAllByState(OrderItemState state, Pageable pageable);


}