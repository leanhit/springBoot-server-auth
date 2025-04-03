package com.example.demo.restaurant.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.restaurant.constant.OrderItemState;
import com.example.demo.restaurant.constant.OrderState;
import com.example.demo.restaurant.dto.OrderDto;
import com.example.demo.restaurant.dto.OrderItemDto;
import com.example.demo.restaurant.dto.request.CreateOrderRequest;
import com.example.demo.restaurant.dto.request.OrderItemPrepareRequest;
import com.example.demo.restaurant.dto.request.OrderItemRequest;
import com.example.demo.restaurant.dto.response.CollectionResponse;
import com.example.demo.restaurant.service.OrderService;
import com.example.demo.restaurant.support.PageUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    @PageableAsQueryParam
    public ResponseEntity<CollectionResponse<OrderDto>> getOrders(@Parameter(hidden = true) Pageable pageable) {
        Page<OrderDto> orders = orderService.findAllOrders(pageable);
        PagedModel.PageMetadata pageMetadata = PageUtils.asPageMetadata(orders);
        return ResponseEntity.ok(CollectionResponse.of(orders.getContent(), pageMetadata));
    }

    @PostMapping("")
    public OrderDto createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @PatchMapping("/{id}")
    public OrderDto updateOrder(@PathVariable UUID id, @RequestBody OrderDto orderDto) {
        orderDto.setId(id);
        return orderService.updateOrder(orderDto);
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable UUID id) {
        orderService.checkoutOrder(id);
    }

    @PostMapping("/{id}/checkout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkoutOrder(@PathVariable UUID id) {
        orderService.updateState(id, OrderState.PAID);
    }

    @PostMapping("/{id}/item")
    public OrderDto addItem(@PathVariable UUID id, @RequestBody List<OrderItemRequest> items) {
        return orderService.addItem(id, items);
    }

    @PostMapping("/{id}/item/{itemId}/prepare")
    public OrderDto prepare(@PathVariable UUID id, @PathVariable UUID itemId, @RequestBody OrderItemPrepareRequest request) {
        return orderService.updateItemState(id, itemId, request.getToState());
    }

    @PostMapping("/{id}/item/{itemId}/cancel")
    public OrderDto cancelItem(@PathVariable UUID id, @PathVariable UUID itemId) {
        return orderService.updateItemState(id, itemId, OrderItemState.CANCELLED);
    }

    @GetMapping("/table/{id}")
    public OrderDto getOrderByTableId(@PathVariable UUID id) {
        return orderService.findCurrentOrderByTableId(id);
    }

    @GetMapping("/items")
    @PageableAsQueryParam
    public CollectionResponse<OrderItemDto> getOrderItems(@Parameter(hidden = true) Pageable pageable, @RequestParam(required = false) OrderItemState state) {
        Page<OrderItemDto> orderItems;
        if (state != null) {
            orderItems = orderService.findAllOrderItems(state, pageable);
        }
        else {
            orderItems = orderService.findAllOrderItems(pageable);
        }
        PagedModel.PageMetadata pageMetadata = PageUtils.asPageMetadata(orderItems);
        return CollectionResponse.of(orderItems.getContent(), pageMetadata);
    }

    @PostMapping("/item/{itemId}/prepare")
    public OrderItemDto prepare(@PathVariable UUID itemId, @RequestBody OrderItemPrepareRequest request) {
        return orderService.updateItemState(itemId, request.getToState());
    }

}
