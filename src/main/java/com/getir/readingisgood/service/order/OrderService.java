package com.getir.readingisgood.service.order;

import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.model.request.order.CreateOrderRequest;
import com.getir.readingisgood.model.request.order.GetOrderByDateRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {
    BaseResponse createOrder(final CreateOrderRequest createOrderRequest);
    BaseResponse getById(final Long id);
    BaseResponse getByDate(final GetOrderByDateRequest getOrderByDateRequest);
    List<OrderDTO> getOrdersByCustomerId(final Long customerId, Pageable page);
}
