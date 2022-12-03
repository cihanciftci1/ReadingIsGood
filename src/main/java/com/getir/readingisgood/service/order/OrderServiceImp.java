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
import com.getir.readingisgood.model.response.errors.BadRequestErrorResponse;
import com.getir.readingisgood.model.response.errors.NotFoundErrorResponse;
import com.getir.readingisgood.model.response.order.CreateOrderResponse;
import com.getir.readingisgood.model.response.order.GetOrderByDateResponse;
import com.getir.readingisgood.model.response.order.GetOrderByIdResponse;
import com.getir.readingisgood.repository.customer.CustomerRepository;
import com.getir.readingisgood.repository.order.BookOrderRepository;
import com.getir.readingisgood.repository.order.OrderRepository;
import com.getir.readingisgood.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService{
    private final OrderRepository orderRepository;
    private final BookOrderRepository bookOrderRepository;
    private final CustomerRepository customerRepository;
    private final BookService bookService;

    @Override
    public BaseResponse createOrder(final CreateOrderRequest createOrderRequest){
        Optional<Customer> customer = customerRepository.findById(createOrderRequest.getCustomerId());
        if(customer.isEmpty()){
            return new NotFoundErrorResponse(Constants.CUSTOMER_NOT_FOUND);
        }

        double amount = 0.0;
        int bookCount=0;

        Order order = orderRepository.save( new Order() );

        List<BookOrder> responseBooks = new ArrayList<>();

        for(CreateOrderRequestBooks requestBook : createOrderRequest.getRequestBooks()){
            Book book = bookService.findByIdAndCheckStock(requestBook.getBookId(), requestBook.getQuantity());
            if(Objects.isNull(book)){
                return new NotFoundErrorResponse("Book id : "+ requestBook.getBookId() + " - " + Constants.BOOKS_NOT_FOUND_OR_INSUFFICIENT_QUANTITY);
            }
            if(requestBook.getQuantity()<1){
                return new BadRequestErrorResponse(Constants.ORDER_QUANTITY_CANT_BE_NEGATIVE);
            }

            BookOrder bookOrder = BookOrder.builder()
                    .title(book.getTitle())
                    .price(book.getPrice())
                    .quantity(requestBook.getQuantity())
                    .bookId(book.getId())
                    .customerId(customer.get().getId())
                    .orderId(order.getId())
                    .build();

            bookOrderRepository.save(bookOrder);


            book.setStock(book.getStock()-requestBook.getQuantity());

            amount += book.getPrice()*requestBook.getQuantity();
            bookCount += requestBook.getQuantity();

            responseBooks.add(bookOrder);
        }

        order.setCustomerId(customer.get().getId());
        order.setTotalAmount(amount);
        order.setBookCount(bookCount);

        orderRepository.save(order);

        return new CreateOrderResponse(Constants.ORDER_CREATED_SUCCESSFULLY,
                OrderDTO.builder()
                        .customerEmail(customer.get().getEmail())
                        .bookOrders(responseBooks)
                        .bookCount(bookCount)
                        .totalAmount(amount)
                        .build());

    }

    @Override
    public BaseResponse getById(final Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            return new NotFoundErrorResponse(Constants.CUSTOMER_NOT_FOUND);
        }

        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()){
            return new NotFoundErrorResponse(Constants.ORDER_NOT_FOUND);
        }

        List<BookOrder> bookOrders = bookOrderRepository.findByOrderId(order.get().getId());

        return new GetOrderByIdResponse(Constants.GET_ORDER_SUCCESSFUL,
                OrderDTO.builder()
                        .customerEmail(customer.get().getEmail())
                        .bookOrders(bookOrders)
                        .bookCount(order.get().getBookCount())
                        .totalAmount(order.get().getTotalAmount())
                        .build());

    }

    @Override
    public BaseResponse getByDate(GetOrderByDateRequest getOrderByDateRequest) {
        Date startDate = getOrderByDateRequest.getStartDate();
        Date endDate = getOrderByDateRequest.getEndDate();

        List<Order> orders = orderRepository.findAllByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(startDate, endDate);
        if(orders.isEmpty()){
            return new NotFoundErrorResponse(Constants.ORDER_NOT_FOUND);
        }

        List<OrderDTO> orderDTOs = new ArrayList<>();

        for(Order order : orders){
            Optional<Customer> customer = customerRepository.findById(order.getCustomerId());

            if(customer.isEmpty()){
                return new NotFoundErrorResponse(Constants.CUSTOMER_NOT_FOUND + " -> with customer id : " + order.getCustomerId());
            }

            List<BookOrder> bookOrders = bookOrderRepository.findByOrderId(order.getId());

            OrderDTO orderDTO = OrderDTO.builder()
                    .customerEmail(customer.get().getEmail())
                    .bookOrders(bookOrders)
                    .bookCount(order.getBookCount())
                    .totalAmount(order.getTotalAmount())
                    .build();

            orderDTOs.add(orderDTO);
        }
        return new GetOrderByDateResponse(Constants.GET_ORDER_SUCCESSFUL, orderDTOs);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(final Long customerId, final Pageable page) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId, page);

        List<OrderDTO> orderDTOs = new ArrayList<>();

        for(Order order : orders){
            Optional<Customer> customer = customerRepository.findById(order.getCustomerId());

            List<BookOrder> bookOrders = bookOrderRepository.findByOrderId(order.getId());

            OrderDTO orderDTO = OrderDTO.builder()
                    .customerEmail(customer.get().getEmail())
                    .bookOrders(bookOrders)
                    .bookCount(order.getBookCount())
                    .totalAmount(order.getTotalAmount())
                    .build();

            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }
}
