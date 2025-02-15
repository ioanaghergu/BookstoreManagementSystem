package org.example.bookstoremanagementsystem.repositories;

import org.example.bookstoremanagementsystem.model.entities.Customer;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT o from Order o where o.customerId = :customerId")
    List<Order> getAllOrdersByCustomerId(int customerId);
}
