package com.example.outsourcingproject.auth.controller;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.auth.dto.request.SignInCustomerRequestDto;
import com.example.outsourcingproject.auth.dto.request.SignUpCustomerRequestDto;
import com.example.outsourcingproject.auth.dto.response.SignInCustomerResponseDto;
import com.example.outsourcingproject.auth.dto.response.SignUpCustomerResponseDto;
import com.example.outsourcingproject.auth.service.CustomerAuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CustomerAuthController {

    private final CustomerAuthServiceImpl customerAuthService;

    // 손님 회원가입
    @PostMapping("/auth/sign-up/customers")
    public ResponseEntity<SignUpCustomerResponseDto> signUpCustomer(
        @RequestBody @Valid SignUpCustomerRequestDto requestDto
    ) {
        SignUpCustomerResponseDto responseDto = customerAuthService.signUp(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 손님 로그인
    @PostMapping("/auth/sign-in/customers")
    public ResponseEntity<SignInCustomerResponseDto> signInCustomer(
        @RequestBody @Valid SignInCustomerRequestDto requestDto
    ) {
        SignInCustomerResponseDto responseDto = customerAuthService.signIn(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 손님 탈퇴
    @AuthCheck("CUSTOMER")
    @DeleteMapping("/customers")
    public ResponseEntity<Void> deleteCustomer(
        @RequestBody String password,
        HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        customerAuthService.deleteCustomer(password, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
