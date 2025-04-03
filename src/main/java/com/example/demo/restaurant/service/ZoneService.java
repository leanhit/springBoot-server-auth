package com.example.demo.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.restaurant.dto.ZoneDto;
import com.example.demo.restaurant.dto.request.CreateZoneRequest;
import com.example.demo.restaurant.entity.TableEntity;
import com.example.demo.restaurant.entity.Zone;
import com.example.demo.restaurant.exception.ResourceNotFoundException;
import com.example.demo.restaurant.mapper.ZoneMapper;
import com.example.demo.restaurant.repository.TableEntityRepository;
import com.example.demo.restaurant.repository.ZoneRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    private final ZoneMapper zoneMapper;

    private final TableEntityRepository tableEntityRepository;

    @Transactional(readOnly = true)
    public Page<ZoneDto> findAllZones(Pageable pageable) {
        return zoneRepository.findAll(pageable).map(zoneMapper::toDto);
    }

    public ZoneDto createZone(CreateZoneRequest zone) {
        return zoneMapper.toDto(zoneRepository.save(zoneMapper.toEntity(zone)));
    }

    public ZoneDto findById(UUID id) {
        return zoneMapper.toDto(zoneRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public ZoneDto updateZone(ZoneDto zoneDto) {
        Zone zone = zoneRepository.findById(zoneDto.getId()).orElseThrow();
        zone = zoneMapper.partialUpdate(zoneDto, zone);
        return zoneMapper.toDto(zoneRepository.save(zone));
    }

    public void deleteZone(UUID id) {
        zoneRepository.deleteById(id);
    }

    @Transactional
    public ZoneDto addTables(UUID id, List<UUID> tableIds) {
        Zone zone = zoneRepository.findById(id).orElseThrow();
        List<TableEntity> tables = tableEntityRepository.findAllById(tableIds);
        if (tables.size() != tableIds.size()) {
            throw new ResourceNotFoundException("Some tables not found");
        }
        for (TableEntity table : tables) {
            table.setZone(zone);
            tableEntityRepository.save(table);
        }
        zone.getTables().addAll(tables);
        return zoneMapper.toDto(zoneRepository.save(zone));
    }

}
