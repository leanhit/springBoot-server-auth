package com.example.demo.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.restaurant.constant.TableStatus;
import com.example.demo.restaurant.dto.TableDto;
import com.example.demo.restaurant.dto.request.CreateTableRequest;
import com.example.demo.restaurant.entity.TableEntity;
import com.example.demo.restaurant.exception.ResourceNotFoundException;
import com.example.demo.restaurant.mapper.TableEntityMapper;
import com.example.demo.restaurant.repository.TableEntityRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableEntityRepository tableEntityRepository;

    private final TableEntityMapper tableEntityMapper;

    public Page<TableDto> findAllTables(Pageable pageable) {
        return tableEntityRepository.findAll(pageable).map(tableEntityMapper::toDto);
    }

    public TableDto createTable(CreateTableRequest table) {
        return tableEntityMapper.toDto(tableEntityRepository.save(tableEntityMapper.toEntity(table)));
    }

    public TableDto findById(UUID id) {
        return tableEntityMapper.toDto(tableEntityRepository.findById(id).orElseThrow(IllegalAccessError::new));
    }

    public TableDto updateTable(TableDto tableDto) {
        TableEntity table = tableEntityRepository.findById(tableDto.getId()).orElse(null);
        if (table == null) {
            throw new ResourceNotFoundException("Table not found");
        }
        table = tableEntityMapper.partialUpdate(tableDto, table);
        return tableEntityMapper.toDto(tableEntityRepository.save(table));
    }

    public void deleteTable(UUID id) {
        tableEntityRepository.deleteById(id);
    }

    public TableDto updateStatus(UUID id, TableStatus status) {
        TableEntity table = tableEntityRepository.findById(id).orElse(null);
        if (table == null) {
            throw new ResourceNotFoundException("Table not found");
        }
        table.setStatus(status);
        return tableEntityMapper.toDto(tableEntityRepository.save(table));
    }

}
