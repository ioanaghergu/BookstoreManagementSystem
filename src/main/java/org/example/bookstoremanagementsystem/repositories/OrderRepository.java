package org.example.bookstoremanagementsystem.repositories;

import org.example.bookstoremanagementsystem.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
