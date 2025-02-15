package org.example.bookstoremanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private List<OrderDTO> orders;
    private List<ReviewDTO> reviews;
}
