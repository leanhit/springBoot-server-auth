package com.example.demo.restaurant.mapper;

import org.mapstruct.*;
import com.example.demo.restaurant.dto.OrderItemDto;
import com.example.demo.restaurant.entity.OrderItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {
    @Mapping(source = "orderTableId", target = "order.tableId")
    OrderItem toEntity(OrderItemDto orderItemDto);

    @Mapping(source = "order.tableId", target = "orderTableId")
    OrderItemDto toDto(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "orderTableId", target = "order.tableId")
    OrderItem partialUpdate(OrderItemDto orderItemDto, @MappingTarget OrderItem orderItem);
}