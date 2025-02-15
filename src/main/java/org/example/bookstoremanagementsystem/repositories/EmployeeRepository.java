package org.example.bookstoremanagementsystem.repositories;

import org.example.bookstoremanagementsystem.model.entities.Employee;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT  o from Order o where o.employeeId = :employeeId")
    List<Order> getAllOrdersByEmployeeId(int employeeId);
}
