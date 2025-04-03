package com.example.demo.restaurant.mapper;

import org.mapstruct.*;
import com.example.demo.restaurant.dto.TableDto;
import com.example.demo.restaurant.dto.request.CreateTableRequest;
import com.example.demo.restaurant.entity.TableEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TableEntityMapper {
    TableEntity toEntity(TableDto tableDto);

    TableEntity toEntity(CreateTableRequest createTableRequest);

    TableDto toDto(TableEntity tableEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TableEntity partialUpdate(TableDto tableDto, @MappingTarget TableEntity tableEntity);
}