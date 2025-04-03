package com.example.demo.restaurant.mapper;

import org.mapstruct.*;
import com.example.demo.restaurant.dto.ItemDto;
import com.example.demo.restaurant.entity.Item;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    Item toEntity(ItemDto itemDto);

    ItemDto toDto(Item item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item partialUpdate(ItemDto itemDto, @MappingTarget Item item);
}