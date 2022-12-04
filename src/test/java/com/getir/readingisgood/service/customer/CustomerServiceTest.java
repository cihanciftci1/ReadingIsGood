package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.enums.ERole;
import com.getir.readingisgood.model.request.customer.GetCustomerOrdersRequest;
import com.getir.readingisgood.model.request.customer.RegisterCustomerRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.customer.GetCustomerOrdersResponse;
import com.getir.readingisgood.model.response.customer.RegisterCustomerResponse;
import com.getir.readingisgood.repository.customer.CustomerRepository;
import com.getir.readingisgood.service.order.OrderServiceImp;
import com.getir.readingisgood.service.role.RoleServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RoleServiceImp roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OrderServiceImp orderService;

    @InjectMocks
    private CustomerServiceImp customerService;

    @Test
    void customer_service_register_should_return_success(){
        //given
        RegisterCustomerRequest registerCustomerRequest = RegisterCustomerRequest.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();

        Role role = Role.builder().name(ERole.USER).build();

        Customer customer = Customer.builder()
                .roles(Set.of(role))
                .email("email")
                .username("username")
                .build();

        BaseResponse expectedResponse = new RegisterCustomerResponse(Constants.REGISTER_SUCCESSFUL);

        //when
        when(customerRepository.existsByUsername(any(String.class))).thenReturn(false);
        when(customerRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(roleService.findByName(any(ERole.class))).thenReturn(role);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        BaseResponse actualResponse = customerService.register(registerCustomerRequest);

        //
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());
    }

    @Test
    void customer_service_get_customer_orders_should_return_orders(){
        //given
        GetCustomerOrdersRequest getCustomerOrdersRequest = GetCustomerOrdersRequest.builder()
                .customerId(1L)
                .pageIndex(0)
                .pageSize(10)
                .build();

        Optional<Customer> customer = Optional.of(Customer.builder()
                .id(1L)
                .email("email")
                .username("username")
                .build());

        OrderDTO orderDTO = OrderDTO.builder()
                .totalAmount(1)
                .bookCount(1)
                .customerEmail("email")
                .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                .build();

        List<OrderDTO> orderDTOs = List.of(orderDTO);
        GetCustomerOrdersResponse expectedResponse = new GetCustomerOrdersResponse(Constants.GET_ORDER_SUCCESSFUL, orderDTOs);

        //when
        when(orderService.getOrdersByCustomerId(any(Long.class), any(Pageable.class))).thenReturn(orderDTOs);
        when(customerRepository.findById(any(Long.class))).thenReturn(customer);
        BaseResponse actualResponse = customerService.getCustomerOrders(getCustomerOrdersRequest);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());


    }


}