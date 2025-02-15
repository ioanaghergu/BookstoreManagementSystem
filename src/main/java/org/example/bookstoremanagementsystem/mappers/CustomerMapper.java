package org.example.bookstoremanagementsystem.mappers;

import org.example.bookstoremanagementsystem.dto.CustomerDTO;
import org.example.bookstoremanagementsystem.model.entities.Customer;

import java.util.Collections;
import java.util.stream.Collectors;

public class CustomerMapper {
    public static CustomerDTO toCustomerDTO(Customer customer) {
        if(customer == null)
            return null;

        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .reviews(customer.getReviews() != null
                        ? customer.getReviews()
                            .stream()
                            .map(ReviewMapper::toReviewDTO)
                            .collect(Collectors.toList())
                        : Collections.emptyList())
                .orders(customer.getOrders() != null
                        ? customer.getOrders()
                                .stream()
                                .map(OrderMapper::toOrderDTO)
                                .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {
        if(customerDTO == null)
            return null;

        return Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .address(customerDTO.getAddress())
                .reviews(customerDTO.getReviews() != null
                        ? customerDTO.getReviews()
                        .stream()
                        .map(ReviewMapper::toReview)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .orders(customerDTO.getOrders() != null
                        ? customerDTO.getOrders()
                        .stream()
                        .map(OrderMapper::toOrder)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();

    }
}
