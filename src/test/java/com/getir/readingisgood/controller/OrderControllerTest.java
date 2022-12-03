package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.order.CreateOrderRequest;
import com.getir.readingisgood.model.request.order.CreateOrderRequestBooks;
import com.getir.readingisgood.model.request.order.GetOrderByDateRequest;
import com.getir.readingisgood.model.response.order.CreateOrderResponse;
import com.getir.readingisgood.model.response.order.GetOrderByDateResponse;
import com.getir.readingisgood.model.response.order.GetOrderByIdResponse;
import com.getir.readingisgood.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void order_controller_create_order_should_return_order_created() throws Exception{
        //given
        List<CreateOrderRequestBooks> requestBooks = List.of(CreateOrderRequestBooks.builder().bookId(1).quantity(1).build());

        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .requestBooks(requestBooks)
                .customerId(1L)
                .build();

        CreateOrderResponse expectedResponse = new CreateOrderResponse(Constants.ORDER_CREATED_SUCCESSFULLY,
                OrderDTO.builder()
                        .customerEmail("email")
                        .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                        .bookCount(1)
                        .totalAmount(1)
                        .build());

        //when
        when(orderService.createOrder(createOrderRequest)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequest)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.data.order.customerEmail", is(expectedResponse.getData().get("order").getCustomerEmail())));

    }

    @Test
    void order_controller_get_order_by_id_should_return_order() throws Exception{
        //given
        Long orderId = 1L;

        GetOrderByIdResponse expectedResponse = new GetOrderByIdResponse(Constants.GET_ORDER_SUCCESSFUL,
                OrderDTO.builder()
                        .customerEmail("email")
                        .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                        .bookCount(1)
                        .totalAmount(1)
                        .build());

        //when
        when(orderService.getById(orderId)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(get("/order/get/{orderId}", orderId));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.data.order.customerEmail", is(expectedResponse.getData().get("order").getCustomerEmail())));

    }

    @Test
    void order_controller_get_order_by_date_should_return_orders() throws Exception{
        //given
        GetOrderByDateRequest getOrderByDateRequest = GetOrderByDateRequest.builder()
                .startDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-12 12:12:12"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-22-22 22:22:22"))
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .totalAmount(1)
                .bookCount(1)
                .customerEmail("email")
                .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                .build();

        GetOrderByDateResponse expectedResponse = new GetOrderByDateResponse(Constants.GET_ORDER_SUCCESSFUL, List.of(orderDTO));

        //when
        when(orderService.getByDate(getOrderByDateRequest)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(get("/order/getByDate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getOrderByDateRequest)));

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.data.orders[0].customerEmail", is(expectedResponse.getData().get("orders").get(0).getCustomerEmail())));



    }

}