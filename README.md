# 📚 Bookstore Management System

## Overview

The **Bookstore Management System** is a backend application designed to manage a bookstore's operations, including managing of customers, employees, books, orders, and reviews. It provides RESTful APIs to perform CRUD operations and functionalities like filtering books, managing orders, and handling reviews. Built using **Java** and **Spring Boot**, this system ensures data validation, error handling, and seamless integration with a relational database.

---

## Business Requirements

1. Customers' information can be created, updated, or removed from the system.
2. Employees' information can be created, updated, or removed from the system.
3. Books can be added, updated, or deleted from the bookstore's catalog.
4. Customers can place orders for one or more books, choose a pay and shipping method, apply coupons and discounts available to their order, and the total price for the order will be computed automatically.
5. Orders can be placed, updated or canceled and their status will be updated automatically.
6. Each order is assigned to an employee for processing.
7. Clients can leave reviews for books they've purchased, including rating and optional comments.
8. Inventory levels are tracked and updated automatically when orders are placed or canceled.
9. Customers can search and filter books by a variety of criteria.(e.g. title, author, rating)
10. Customers can access their order history and see their previous orders, including total prices and ordering dates.

---

## Implemented Features

### 1. **Customers Management**
- ✅ Add, update, and remove customers from the system.
- ✅ List all customers.
- ✅ List all orders for a customer.

### 2. **Books Management**
- ✅ Add, update, and remove books from the catalog.
- ✅ List all books.
- ✅ Search books by title, author, or average rating.
- ✅ Manage inventory levels for books.

### 3. **Employees Management**
- ✅ Add, update, and remove employees from the system.
- ✅ List all employees.
- ✅ Assign employees to orders for processing.
- ✅ List all orders assigned to an employee for processing.

### 4. **Orders Management**
- ✅ Create, update, and cancel orders.
- ✅ Compute total price per order and update status.

### 5. **Reviews Management**
- ✅ Add, update, and delete reviews for a book.
- ✅ List reviews for a specific book.
  
---

## REST Endpoints

| Entity      | Endpoint                          | Methods          | Description                                      |
|-------------|-----------------------------------|------------------|--------------------------------------------------|
| **Books**   | `/books`                          | GET, POST        | Retrieve all books, add a new book.            |
|             | `/books/{id}`                     | GET, PUT, DELETE | Retrieve, update, delete a book by ID.        |
|             | `/books/title?title={title}`      | GET              | Retrieve books by title.                         |
|             | `/books/author?author={author}`   | GET              | Retrieve books by author.                        |
|             | `/books/rating?avgRating={rating}`| GET              | Retrieve books by average rating.                |
| **Customers**| `/customers`                      | GET, POST        | Retrieve all customers, add a new customer.    |
|             | `/customers/{id}`                 | GET, PUT, DELETE | Retrieve, update, delete a customer by ID.    |
|             | `/customers/orders?Customer ID={id}` | GET          | Retrieve all orders for a customer.              |
| **Employees**| `/employees`                      | GET, POST        | Retrieve all employees, add a new employee.    |
|             | `/employees/{id}`                 | GET, PUT, DELETE | Retrieve, update, delete an employee by ID.   |
|             | `/employees/orders?Employee ID={id}` | GET          | Retrieve all orders for an employee.             |
| **Orders**  | `/orders`                         | GET, POST        | Retrieve all orders, add a new order.          |
|             | `/orders/{id}`                    | GET, DELETE      | Retrieve, delete an order by ID.               |
| **Reviews** | `/reviews`                        | GET, POST        | Retrieve all reviews, add a new review.        |
|             | `/reviews/{id}`                   | GET, PUT, DELETE | Retrieve, update, delete a review by ID.      |
|             | `/reviews/searchByBook?Book Title={title}` | GET      | Retrieve reviews for a specific book.            |

---

## Database Diagram and Relationships

The application stores data in a **MySql** database.

### Database Diagram

![database](https://github.com/user-attachments/assets/4763afda-ceb9-4c31-8ed9-8f76e758c64f)


### Entities and Relationships
1. **Customer**: a customer can place multiple orders, can add multiple reviews
   - One-to-Many relationship with **Order**.
   - One-to-Many relationship with **Review**.

2. **Employee**: an employee can process multiple orders
   - One-to-Many relationship with **Order**.

3. **Book**: a book can have multiple reviews, can be ordered multiple times
   - One-to-Many relationship with **OrderItem**.
   - One-to-Many relationship with **Review**.

4. **Order**: multiple orders can belong to one customer, multiple orders can be processed by the same employee, an order can contain multiple books
   - Many-to-One relationship with **Customer**.
   - Many-to-One relationship with **Employee**.
   - One-to-Many relationship with **OrderItem**.

5. **OrderItem**: multiple books(items in an order) can be found in a single order
   - Many-to-One relationship with **Order**.
   - Many-to-One relationship with **Book**.

6. **Review**: multiple reviews can belong to one customer, multiple reviews can be left on one book
   - Many-to-One relationship with **Customer**.
   - Many-to-One relationship with **Book**.

---

### Validations
The implemented classes have been validated using existing validation constraints, but also custom validations such as `@ValidEmail` or `@ValidPhoneNumber`.

![image](https://github.com/user-attachments/assets/78ee2566-37c2-4ebe-a283-e6c151c73f9f)

---

### Unit tests
Unit tests have been implemented for each controller and service.

![image](https://github.com/user-attachments/assets/d8f91973-4cdf-4fca-9440-0167e8683565)
![image](https://github.com/user-attachments/assets/d4b7489e-4e60-4748-baaf-ed28157ceb2f)

---
### Swagger Documenentation
Each endpoint has been documented using Swagger. The documentation can be found at: http://localhost:8080/swagger-ui/index.html#/

![image](https://github.com/user-attachments/assets/e0919d2b-bd69-4733-b6e3-1149512f2597)
![image](https://github.com/user-attachments/assets/0252222c-3224-49ea-8b0c-fd0cda3d1220)


