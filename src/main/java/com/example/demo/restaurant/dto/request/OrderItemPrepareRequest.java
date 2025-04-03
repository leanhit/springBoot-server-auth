package com.example.demo.restaurant.dto.request;

import lombok.Data;
import com.example.demo.restaurant.constant.OrderItemState;

@Data
public class OrderItemPrepareRequest {

    private OrderItemState toState;

}
