package com.javaproject.controllers;

import com.javaproject.entity.Customers;
import com.javaproject.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Customers")
@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @GetMapping
    public List<Customers> GetAll(){
        return customerRepository.findAll();
    }
    @PostMapping
    public void AddCustomer(@RequestBody Customers customer){
        customerRepository.save(customer);
    }
}
