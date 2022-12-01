package com.getir.readingisgood.service.customer;

import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.repository.customer.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final ICustomerRepository customerRepository;


    public Optional<Customer> findByUsername(final String username){
        return customerRepository.findByUsername(username);
    }

    public boolean existsByUsername(final String username){
        return customerRepository.existsByUsername(username);
    }

    public boolean existsByEmail(final String email){
        return customerRepository.existsByEmail(email);
    }

    public void save(final Customer customer){
        customerRepository.save(customer);
    }
}
