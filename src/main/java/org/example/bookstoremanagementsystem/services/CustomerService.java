package org.example.bookstoremanagementsystem.services;

import org.example.bookstoremanagementsystem.dto.CustomerDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.CustomerMapper;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Customer;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public CustomerDTO findCustomerById(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));

        return CustomerMapper.toCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerMapper::toCustomerDTO).toList();
    }

    public List<OrderDTO> getAllOrdersForACustomer(int id) {
        List<Order> orders = customerRepository.getAllOrdersByCustomerId(id);
        return orders.stream().map(OrderMapper::toOrderDTO).toList();
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toCustomer(customerDTO);
        customer.setReviews(null); //a new inserted customer doesn't have any reviews
        return CustomerMapper.toCustomerDTO(customerRepository.save(customer));
    }

    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));

        if(customerDTO.getName() != null)
            customer.setName(customerDTO.getName());

        if(customerDTO.getAddress() != null)
            customer.setAddress(customerDTO.getAddress());

        if(customerDTO.getPhone() != null)
            customer.setPhone(customerDTO.getPhone());

        if(customerDTO.getEmail() != null)
            customer.setEmail(customerDTO.getEmail());

        Customer updatedCustomer = customerRepository.save(customer);

        return CustomerMapper.toCustomerDTO(updatedCustomer);
    }

    public void deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer with id " + id + " not found"));
        customerRepository.delete(customer);
    }
}
