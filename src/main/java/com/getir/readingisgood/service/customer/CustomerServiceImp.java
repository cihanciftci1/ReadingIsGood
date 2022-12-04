package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Role;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.enums.ERole;
import com.getir.readingisgood.model.request.customer.GetCustomerOrdersRequest;
import com.getir.readingisgood.model.request.customer.LoginCustomerRequest;
import com.getir.readingisgood.model.request.customer.RegisterCustomerRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.customer.GetCustomerOrdersResponse;
import com.getir.readingisgood.model.response.customer.LoginCustomerResponse;
import com.getir.readingisgood.model.response.customer.RegisterCustomerResponse;
import com.getir.readingisgood.model.response.errors.AuthenticationErrorResponse;
import com.getir.readingisgood.model.response.errors.BadRequestErrorResponse;
import com.getir.readingisgood.model.response.errors.NotFoundErrorResponse;
import com.getir.readingisgood.repository.customer.CustomerRepository;
import com.getir.readingisgood.service.order.OrderService;
import com.getir.readingisgood.service.role.RoleService;
import com.getir.readingisgood.service.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImp implements CustomerService{
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final JWTGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    @Override
    public BaseResponse register(final RegisterCustomerRequest registerCustomerRequest){
        if(customerRepository.existsByUsername(registerCustomerRequest.getUsername())){
            return new BadRequestErrorResponse(Constants.USERNAME_ALREADY_TAKEN);
        }else if (customerRepository.existsByEmail(registerCustomerRequest.getEmail())){
            return new BadRequestErrorResponse(Constants.EMAIL_ALREADY_TAKEN);
        }

        Role role = roleService.findByName(ERole.USER);

        Customer customer = Customer.builder()
                .username(registerCustomerRequest.getUsername())
                .email(registerCustomerRequest.getEmail())
                .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                .roles(Set.of(role))
                .build();

        customerRepository.save(customer);
        logger.info("{} registered at {}", customer, LocalDateTime.now());

        return new RegisterCustomerResponse(Constants.REGISTER_SUCCESSFUL);
    }

    @Override
    public BaseResponse login(final LoginCustomerRequest loginCustomerRequest){
        String token;
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginCustomerRequest.getUsername(), loginCustomerRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtGenerator.generateToken(authentication);
        }catch (AuthenticationException e){
            return new AuthenticationErrorResponse(Constants.LOGIN_FAILED);
        }
        return new LoginCustomerResponse(Constants.LOGIN_SUCCESFUL, token);
    }

    @Override
    public BaseResponse getCustomerOrders(final GetCustomerOrdersRequest getCustomerOrdersRequest) {
        Long id = getCustomerOrdersRequest.getCustomerId();

        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            return new NotFoundErrorResponse(Constants.CUSTOMER_NOT_FOUND);
        }

        int pageIndex = Objects.nonNull(getCustomerOrdersRequest.getPageIndex()) ? getCustomerOrdersRequest.getPageIndex() : 0;
        int pageSize = Objects.nonNull(getCustomerOrdersRequest.getPageSize()) ? getCustomerOrdersRequest.getPageSize() : 10;

        Pageable page = PageRequest.of(pageIndex, pageSize);

        List<OrderDTO> orderDTOs = orderService.getOrdersByCustomerId(id, page);

        if(orderDTOs.isEmpty()){
            return new NotFoundErrorResponse(Constants.ORDER_NOT_FOUND);
        }


        return new GetCustomerOrdersResponse(Constants.GET_ORDER_SUCCESSFUL, orderDTOs);
    }
}
