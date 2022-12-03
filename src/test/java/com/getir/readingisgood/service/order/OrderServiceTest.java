package com.getir.readingisgood.service.order;

import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.BookOrder;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.order.CreateOrderRequest;
import com.getir.readingisgood.model.request.order.CreateOrderRequestBooks;
import com.getir.readingisgood.model.request.order.GetOrderByDateRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.order.CreateOrderResponse;
import com.getir.readingisgood.model.response.order.GetOrderByDateResponse;
import com.getir.readingisgood.model.response.order.GetOrderByIdResponse;
import com.getir.readingisgood.repository.customer.CustomerRepository;
import com.getir.readingisgood.repository.order.BookOrderRepository;
import com.getir.readingisgood.repository.order.OrderRepository;
import com.getir.readingisgood.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookService bookService;

    @Mock
    private BookOrderRepository bookOrderRepository;

    @InjectMocks
    private OrderServiceImp orderService;

    @Test
    void order_service_create_order_should_return_created_order(){
        //given
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .requestBooks(List.of(CreateOrderRequestBooks.builder().bookId(1).quantity(1).build()))
                .customerId(1L)
                .build();

        Optional<Customer> customer = Optional.of(Customer.builder()
                .id(1L)
                .username("userName")
                .email("email")
                .build());

        Order order = Order.builder()
                .id(1L)
                .bookCount(1)
                .totalAmount(1)
                .build();

        Book book  = Book.builder()
                .id(1)
                .price(1)
                .stock(1)
                .title("title")
                .build();

        BookOrder bookOrder = BookOrder.builder()
                .orderId(1L)
                .price(1)
                .quantity(1)
                .bookId(1)
                .title("title")
                .customerId(1L)
                .build();

        List<BookOrder> bookOrders = List.of(bookOrder);

        CreateOrderResponse expectedResponse = new CreateOrderResponse(Constants.ORDER_CREATED_SUCCESSFULLY,
                OrderDTO.builder()
                        .customerEmail("email")
                        .bookOrders(bookOrders)
                        .bookCount(1)
                        .totalAmount(1)
                        .build());

        //when
        when(customerRepository.findById(any(Long.class))).thenReturn(customer);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(bookService.findByIdAndCheckStock(any(Integer.class), any(int.class))).thenReturn(book);
        when(bookOrderRepository.save(any(BookOrder.class))).thenReturn(bookOrder);
        BaseResponse actualResponse = orderService.createOrder(createOrderRequest);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());

    }

    @Test
    void order_service_get_order_by_id_should_return_order(){
        //given
        Long orderId = 1L;

        Optional<Customer> customer = Optional.of(Customer.builder()
                .id(1L)
                .username("userName")
                .email("email")
                .build());

        Optional<Order> order = Optional.of(Order.builder()
                .id(1L)
                .bookCount(1)
                .totalAmount(1)
                .build());

        BookOrder bookOrder = BookOrder.builder()
                .orderId(1L)
                .price(1)
                .quantity(1)
                .bookId(1)
                .title("title")
                .customerId(1L)
                .build();

        List<BookOrder> bookOrders = List.of(bookOrder);

        GetOrderByIdResponse expectedResponse = new GetOrderByIdResponse(Constants.GET_ORDER_SUCCESSFUL,
                OrderDTO.builder()
                        .customerEmail("email")
                        .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                        .bookCount(1)
                        .totalAmount(1)
                        .build());

        //when
        when(customerRepository.findById(any(Long.class))).thenReturn(customer);
        when(orderRepository.findById(any(Long.class))).thenReturn(order);
        when(bookOrderRepository.findByOrderId(any(Long.class))).thenReturn(bookOrders);
        BaseResponse actualResponse = orderService.getById(orderId);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());

    }

    @Test
    void order_service_get_by_date_should_return_orders() throws Exception{
        //given
        GetOrderByDateRequest getOrderByDateRequest = GetOrderByDateRequest.builder()
                .startDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-12 12:12:12"))
                .endDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-22-22 22:22:22"))
                .build();

        Optional<Order> order = Optional.of(Order.builder()
                .id(1L)
                .customerId(1L)
                .bookCount(1)
                .totalAmount(1)
                .build());

        List<Order> orders = List.of(order.get());

        OrderDTO orderDTO = OrderDTO.builder()
                .totalAmount(1)
                .bookCount(1)
                .customerEmail("email")
                .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                .build();

        List<OrderDTO> orderDTOs = List.of(orderDTO);

        Optional<Customer> customer = Optional.of(Customer.builder()
                .id(1L)
                .username("userName")
                .email("email")
                .build());

        BookOrder bookOrder = BookOrder.builder()
                .orderId(1L)
                .price(1)
                .quantity(1)
                .bookId(1)
                .title("title")
                .customerId(1L)
                .build();

        List<BookOrder> bookOrders = List.of(bookOrder);

        GetOrderByDateResponse expectedResponse = new GetOrderByDateResponse(Constants.GET_ORDER_SUCCESSFUL, orderDTOs);

        //when
        when(orderRepository.findAllByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(any(Date.class), any(Date.class))).thenReturn(orders);
        when(customerRepository.findById(any(Long.class))).thenReturn(customer);
        when(bookOrderRepository.findByOrderId(any(Long.class))).thenReturn(bookOrders);
        BaseResponse actualResponse = orderService.getByDate(getOrderByDateRequest);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());

    }

    @Test
    void order_service_get_orders_by_customer_id_should_return_orders(){
        Long customerId = 1L;
        Pageable page = PageRequest.of(1,1);

        Optional<Order> order = Optional.of(Order.builder()
                .id(1L)
                .customerId(1L)
                .bookCount(1)
                .totalAmount(1)
                .build());
        List<Order> orders = List.of(order.get());


        Optional<Customer> customer = Optional.of(Customer.builder()
                .id(1L)
                .username("userName")
                .email("email")
                .build());

        BookOrder bookOrder = BookOrder.builder()
                .orderId(1L)
                .price(1)
                .quantity(1)
                .bookId(1)
                .title("title")
                .customerId(1L)
                .build();
        List<BookOrder> bookOrders = List.of(bookOrder);

        OrderDTO orderDTO = OrderDTO.builder()
                .totalAmount(1)
                .bookCount(1)
                .customerEmail("email")
                .bookOrders(List.of(BookOrder.builder().bookId(1).quantity(1).price(1).build()))
                .build();
        List<OrderDTO> expectedResponse = List.of(orderDTO);

        //when
        when(orderRepository.findAllByCustomerId(any(Long.class), any(Pageable.class))).thenReturn(orders);
        when(customerRepository.findById(any(Long.class))).thenReturn(customer);
        when(bookOrderRepository.findByOrderId(any(Long.class))).thenReturn(bookOrders);
        List<OrderDTO> actualRespone = orderService.getOrdersByCustomerId(customerId, page);

        //then
        assertThat(actualRespone).isNotNull();
        assertThat(actualRespone.get(0).getCustomerEmail()).isEqualTo(expectedResponse.get(0).getCustomerEmail());

    }
}