package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.order.CreateOrderRequest;
import com.getir.readingisgood.model.request.order.GetOrderByDateRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public BaseResponse createOrder(@RequestBody @Valid final CreateOrderRequest createOrderRequest){
        return orderService.createOrder(createOrderRequest);
    }

    @GetMapping("/get/{orderId}")
    public BaseResponse getOrderById(@PathVariable Long orderId){
        return orderService.getById(orderId);
    }

    @GetMapping("/getByDate")
    public BaseResponse getOrderByDate(@RequestBody GetOrderByDateRequest getOrderByDateRequest){
        return orderService.getByDate(getOrderByDateRequest);
    }

}
