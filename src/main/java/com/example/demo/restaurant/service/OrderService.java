package com.example.demo.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.restaurant.constant.OrderItemState;
import com.example.demo.restaurant.constant.OrderState;
import com.example.demo.restaurant.constant.TableStatus;
import com.example.demo.restaurant.dto.ItemDto;
import com.example.demo.restaurant.dto.OrderDto;
import com.example.demo.restaurant.dto.OrderItemDto;
import com.example.demo.restaurant.dto.TableDto;
import com.example.demo.restaurant.dto.request.CreateOrderRequest;
import com.example.demo.restaurant.dto.request.OrderItemRequest;
import com.example.demo.restaurant.entity.Order;
import com.example.demo.restaurant.entity.OrderItem;
import com.example.demo.restaurant.exception.ResourceNotFoundException;
import com.example.demo.restaurant.mapper.OrderItemMapper;
import com.example.demo.restaurant.mapper.OrderMapper;
import com.example.demo.restaurant.repository.OrderItemRepository;
import com.example.demo.restaurant.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final TableService tableService;

    private final ItemService itemService;

    public Page<OrderDto> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        TableDto tableDto = tableService.findById(request.getTableId());
        if (tableDto == null) {
            throw new ResourceNotFoundException("Table not found");
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setTableId(tableDto.getId());
        orderDto.setState(OrderState.PENDING);
        tableService.updateStatus(tableDto.getId(), TableStatus.OCCUPIED);
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    public OrderDto findById(UUID id) {
        return orderMapper.toDto(orderRepository.findById(id).orElse(null));
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getId()).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        order = orderMapper.partialUpdate(orderDto, order);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderDto updateState(UUID id, OrderState state) {
        OrderDto orderDto = findById(id);
        if (orderDto == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        orderDto.setState(state);
        tableService.updateStatus(orderDto.getTableId(), TableStatus.AVAILABLE);
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    @Transactional
    public OrderDto addItem(UUID orderId, List<OrderItemRequest> itemRequests) {
        OrderDto orderDto = findById(orderId);
        if (orderDto == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        for (var itemRequest : itemRequests) {
            if (itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
        }
        List<UUID> itemIds = itemRequests.stream().map(OrderItemRequest::getItemId).toList();
        List<ItemDto> items = itemService.findByIds(itemIds);
        if (items.size() != itemIds.size()) {
            throw new ResourceNotFoundException("Item not found");
        }
        for (var item : items) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setItemId(item.getId());
            orderItemDto.setPrice(item.getPrice());
            orderItemDto.setQuantity(itemRequests.stream().filter(i -> i.getItemId().equals(item.getId())).findFirst().get().getQuantity());
            orderItemDto.setState(OrderItemState.PENDING);
            orderItemDto.setSpecifications(itemRequests.stream().filter(i -> i.getItemId().equals(item.getId())).findFirst().get().getSpecifications());
            orderDto.getItems().add(orderItemDto);
        }
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    @Transactional
    public OrderDto updateItemState(UUID orderId, UUID itemId, OrderItemState state) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        if (order.getState() != OrderState.PENDING) {
            throw new IllegalStateException("Order is not pending");
        }
        OrderItem orderItem = order.getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().orElse(null);
        if (orderItem == null) {
            throw new ResourceNotFoundException("Item not found");
        }
        orderItem.setState(state);
        orderItemRepository.save(orderItem);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public OrderItemDto updateItemState(UUID itemId, OrderItemState state) {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElse(null);
        if (orderItem == null) {
            throw new ResourceNotFoundException("Item not found");
        }
        orderItem.setState(state);
        return orderItemMapper.toDto(orderItemRepository.save(orderItem));
    }

    @Transactional
    public OrderDto checkoutOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        order.setState(OrderState.PAID);
        tableService.updateStatus(order.getTableId(), TableStatus.AVAILABLE);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public OrderDto findByTableId(UUID tableId) {
        return orderMapper.toDto(orderRepository.findByTableId(tableId).orElse(null));
    }

    public Page<OrderItemDto> findAllOrderItems(Pageable pageable) {
        return orderItemRepository.findAll(pageable).map(orderItemMapper::toDto);
    }

    public Page<OrderItemDto> findAllOrderItems(OrderItemState state, Pageable pageable) {
        return orderItemRepository.findAllByState(state, pageable).map(orderItemMapper::toDto);
    }

    public OrderDto findCurrentOrderByTableId(UUID tableId) {
        TableDto tableDto = tableService.findById(tableId);
        if (tableDto == null) {
            throw new ResourceNotFoundException("Table not found");
        }
        if (tableDto.getStatus() != TableStatus.OCCUPIED) {
            return null;
        }
        return orderMapper.toDto(orderRepository.findFirstByTableIdAndStateOrderByCreatedDateDesc(tableId, OrderState.PENDING).orElse(null));
    }

}
