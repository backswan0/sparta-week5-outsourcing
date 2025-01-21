package com.example.outsourcingproject.auth.controller;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.auth.dto.request.SignInOwnerRequestDto;
import com.example.outsourcingproject.auth.dto.request.SignUpOwnerRequestDto;
import com.example.outsourcingproject.auth.dto.response.SignInOwnerResponseDto;
import com.example.outsourcingproject.auth.dto.response.SignUpOwnerResponseDto;
import com.example.outsourcingproject.auth.service.OwnerAuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OwnerAuthController {

    private final OwnerAuthServiceImpl ownerAuthService;

    // 사장님 회원가입
    @PostMapping("/auth/sign-up/owners")
    public ResponseEntity<SignUpOwnerResponseDto> signUpOwner(
        @RequestBody @Valid SignUpOwnerRequestDto requestDto
    ) {
        SignUpOwnerResponseDto responseDto = ownerAuthService.signUp(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 사장님 로그인
    @PostMapping("/auth/sign-in/owners")
    public ResponseEntity<SignInOwnerResponseDto> signInOwner(
        @RequestBody @Valid SignInOwnerRequestDto requestDto
    ) {
        SignInOwnerResponseDto signInOwnerResponseDto = ownerAuthService.signIn(requestDto);

        return new ResponseEntity<>(signInOwnerResponseDto, HttpStatus.OK);
    }

    // 사장님 탈퇴
    @AuthCheck("OWNER")
    @DeleteMapping("/owners")
    public ResponseEntity<Void> deleteOwner(
        @RequestBody String password,
        HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        ownerAuthService.deleteOwner(password, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
