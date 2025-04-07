package com.example.demo.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.restaurant.dto.ItemDto;
import com.example.demo.restaurant.exception.ResourceNotFoundException;
import com.example.demo.restaurant.mapper.ItemMapper;
import com.example.demo.restaurant.repository.ItemRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    @Transactional(readOnly = true)
    public Page<ItemDto> findAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable).map(itemMapper::toDto);
    }

    public ItemDto createItem(ItemDto itemDto) {
        return itemMapper.toDto(itemRepository.save(itemMapper.toEntity(itemDto)));
    }

    public ItemDto findById(UUID id) {
        return itemMapper.toDto(itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found")));
    }

    public ItemDto updateItem(ItemDto itemDto) {
        return itemMapper.toDto(itemRepository.save(itemMapper.toEntity(itemDto)));
    }

    public void deleteItem(UUID id) {
        itemRepository.deleteById(id);
    }

    public List<ItemDto> findByIds(List<UUID> ids) {
        return itemRepository.findAllById(ids).stream().map(itemMapper::toDto).toList();
    }

}
