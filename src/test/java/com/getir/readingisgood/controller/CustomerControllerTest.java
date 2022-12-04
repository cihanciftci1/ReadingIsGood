package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.customer.GetCustomerOrdersRequest;
import com.getir.readingisgood.model.request.customer.RegisterCustomerRequest;
import com.getir.readingisgood.model.response.customer.GetCustomerOrdersResponse;
import com.getir.readingisgood.model.response.customer.RegisterCustomerResponse;
import com.getir.readingisgood.service.customer.CustomerService;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest{
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void customer_controller_register_should_return_success() throws Exception{
        //given
        RegisterCustomerRequest registerCustomerRequest = RegisterCustomerRequest.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();

        RegisterCustomerResponse expectedResponse = new RegisterCustomerResponse(Constants.REGISTER_SUCCESSFUL);

        //when
        when(customerService.register(registerCustomerRequest)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(post("/customer/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerCustomerRequest)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())));
    }

    @Test
    void customer_controller_get_customer_orders_should_return_orders() throws Exception{
        //given
        GetCustomerOrdersRequest getCustomerOrdersRequest = GetCustomerOrdersRequest.builder()
                .customerId(1L)
                .pageIndex(0)
                .pageSize(10)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .totalAmount(1)
                .bookCount(1)
                .customerEmail("email")
                .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                .build();

        GetCustomerOrdersResponse expectedResponse = new GetCustomerOrdersResponse(Constants.GET_ORDER_SUCCESSFUL, List.of(orderDTO));


        //when
        when(customerService.getCustomerOrders(getCustomerOrdersRequest)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(get("/customer/orders/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCustomerOrdersRequest)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.data.orders[0].customerEmail", is(expectedResponse.getData().get("orders").get(0).getCustomerEmail())));

    }

}