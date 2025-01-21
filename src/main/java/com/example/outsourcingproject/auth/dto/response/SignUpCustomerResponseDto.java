package com.example.outsourcingproject.auth.dto.response;

import com.example.outsourcingproject.common.entity.Customer;
import lombok.Getter;

@Getter
public class SignUpCustomerResponseDto {

    private final Long id;
    private final String email;

    public SignUpCustomerResponseDto(Customer customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
    }
}
