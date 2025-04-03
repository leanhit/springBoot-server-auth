package com.example.demo.restaurant.mapper;

import org.mapstruct.*;
import com.example.demo.restaurant.dto.OrderDto;
import com.example.demo.restaurant.entity.Order;
import com.example.demo.restaurant.mapper.OrderItemMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderItemMapper.class})
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);

    @AfterMapping
    default void linkOrderItems(@MappingTarget Order order) {
        order.getItems().forEach(orderItem -> orderItem.setOrder(order));
    }

    OrderDto toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(OrderDto orderDto, @MappingTarget Order order);
}