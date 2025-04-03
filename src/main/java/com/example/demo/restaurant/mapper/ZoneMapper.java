package com.example.demo.restaurant.mapper;

import org.mapstruct.*;
import com.example.demo.restaurant.dto.ZoneDto;
import com.example.demo.restaurant.dto.request.CreateZoneRequest;
import com.example.demo.restaurant.entity.Zone;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ZoneMapper {
    Zone toEntity(ZoneDto zoneDto);

    Zone toEntity(CreateZoneRequest zoneDto);

    @AfterMapping
    default void linkTables(@MappingTarget Zone zone) {
        zone.getTables().forEach(table -> table.setZone(zone));
    }

    ZoneDto toDto(Zone zone);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Zone partialUpdate(ZoneDto zoneDto, @MappingTarget Zone zone);
}