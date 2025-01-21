package com.example.outsourcingproject.domain.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.outsourcingproject.auth.controller.CustomerAuthController;
import com.example.outsourcingproject.auth.dto.request.SignUpCustomerRequestDto;
import com.example.outsourcingproject.auth.dto.response.SignUpCustomerResponseDto;
import com.example.outsourcingproject.auth.service.CustomerAuthServiceImpl;
import com.example.outsourcingproject.common.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CustomerAuthController.class)
public class CustomerAuthControllerTest {

    public static final String SignUpUrl = "/auth/sign-up/customers";

    @MockitoBean
    CustomerAuthServiceImpl customerAuthService;

    @InjectMocks
    private CustomerAuthController customerAuthController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 손님_회원가입_컨트롤러_호출_성공() throws Exception {
        // given

        // Customer 객체를 mock
        Customer mockCustomer = Mockito.mock(Customer.class);
        when(mockCustomer.getEmail()).thenReturn("email@email.com");
        when(mockCustomer.getId()).thenReturn(1L);  // mock id 값 설정

        SignUpCustomerRequestDto signUpCustomerRequestDto = new SignUpCustomerRequestDto("email@email.com", "password");
        SignUpCustomerResponseDto signUpCustomerResponseDto = new SignUpCustomerResponseDto(mockCustomer);

        when(customerAuthService.signUp(any(SignUpCustomerRequestDto.class))).thenReturn(signUpCustomerResponseDto);

        // when
        ResultActions result = this.mockMvc.perform(
            post(SignUpUrl) // post 요청
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpCustomerRequestDto))
        );


        // then
        result.andExpect(status().isCreated()) // HTTP 201 Created 응답 기대
            .andExpect(jsonPath("$.id").value(1L)) // ID가 1L인지 확인
            .andExpect(jsonPath("$.email").value("email@email.com")); // 이메일 값이 요청한 이메일과 일치하는지 확인
    }

}
