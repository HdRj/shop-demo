package com.example.shop.service.system;

import com.example.shop.exceptions.ShopException;
import com.example.shop.model.dto.request.OrderRequestDTO;
import com.example.shop.model.entity.Customer;
import com.example.shop.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer getOrCreateCustomer(OrderRequestDTO orderRequestDto) throws ShopException {
        Optional<Customer> customerOptional = customerRepository.findByEmail(orderRequestDto.email());
        if(customerOptional.isPresent()){
            if(customerOptional.get().getPhone().equalsIgnoreCase(orderRequestDto.phone())){
                return customerOptional.get();
            }else{
                throw new ShopException("Customer with this email already exists but has another phone number");
            }
        }else {
            customerOptional = customerRepository.findByPhone(orderRequestDto.phone());
            if(customerOptional.isPresent()){
                throw new ShopException("Customer with this phone number already exists but has another email");
            }

            Customer customer = new Customer(
                    orderRequestDto.email(),
                    orderRequestDto.phone(),
                    orderRequestDto.name()
            );
            return customerRepository.save(customer);
        }
    }

    public Customer getByEmailAndPhone(String email, String phone) throws ShopException {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if(customerOptional.isPresent()){
            if(customerOptional.get().getPhone().replace("+","").trim().equalsIgnoreCase(phone.replace("+","").trim())){
                return customerOptional.get();
            }else{
                throw new ShopException("Customer with this email already exists but has another phone number");
            }
        }else{
            throw new ShopException("Customer with this email doesn't exist");
        }
    }
}
