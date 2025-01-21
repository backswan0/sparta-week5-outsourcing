package com.example.outsourcingproject.auth.dto.response;

import com.example.outsourcingproject.common.entity.Owner;
import lombok.Getter;

@Getter
public class SignUpOwnerResponseDto {

    private final Long id;
    private final String email;

    public SignUpOwnerResponseDto(Owner owner) {
        this.id = owner.getId();
        this.email = owner.getEmail();
    }
}
