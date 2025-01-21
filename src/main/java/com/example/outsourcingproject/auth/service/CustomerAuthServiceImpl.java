package com.example.outsourcingproject.auth.service;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.auth.dto.request.SignInCustomerRequestDto;
import com.example.outsourcingproject.auth.dto.request.SignUpCustomerRequestDto;
import com.example.outsourcingproject.auth.dto.response.SignInCustomerResponseDto;
import com.example.outsourcingproject.auth.dto.response.SignUpCustomerResponseDto;
import com.example.outsourcingproject.auth.repository.CustomerAuthRepository;
import com.example.outsourcingproject.common.entity.Customer;
import com.example.outsourcingproject.common.exception.CustomException;
import com.example.outsourcingproject.common.exception.ErrorCode;
import com.example.outsourcingproject.common.utils.JwtUtil;
import com.example.outsourcingproject.common.utils.PasswordEncoder;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final CustomerAuthRepository customerAuthRepository;
    private final JwtUtil jwtUtil;
    //    private final TokenBlacklistService tokenBlacklistService;
    PasswordEncoder bcrypt = new PasswordEncoder();


    @Override
    public SignUpCustomerResponseDto signUp(SignUpCustomerRequestDto requestDto) {

        // 등록된 아이디(이메일) 여부 확인
        boolean isExistingEmail = customerAuthRepository.findByEmail(requestDto.getEmail())
            .isPresent();

        if (isExistingEmail) {
            log.info("이미 존재하는 이메일입니다. >> {}", requestDto.getEmail());
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        }
        Customer customerToSave = new Customer(
            requestDto.getEmail(),
            bcrypt.encode(requestDto.getPassword())
        );

        Customer savedCustomer = customerAuthRepository.save(customerToSave);

        log.info("손님 {} 회원가입 완료", requestDto.getEmail());
        return new SignUpCustomerResponseDto(savedCustomer);
    }

    @Override
    public SignInCustomerResponseDto signIn(SignInCustomerRequestDto requestDto) {

        // 탈퇴하지 않은 손님들 중에서 이메일 값이 일치하는 손님 추출
        Customer foundCustomer = customerAuthRepository.findByEmailAndIsDeletedFalse(
                requestDto.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

        String encodedPassword = foundCustomer.getPassword();

        boolean isPasswordMismatching = !bcrypt.matches(
            requestDto.getPassword(),
            encodedPassword
        );

        if (isPasswordMismatching) {
            log.info("아이디 또는 비밀번호가 잘못되었습니다.");
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        log.info("손님 {} 로그인 완료", requestDto.getEmail());

        String token = jwtUtil.createToken(
            requestDto.getEmail(),
            foundCustomer.getAuthority()
        );

        // 앞의 7글자 ('Bearer ')를 제외한 실제 토큰 부분만 추출
        String actualToken = token.substring(7);

        return new SignInCustomerResponseDto(actualToken);
    }

    @AuthCheck("CUSTOMER")
    @Override
    @Transactional
    public void deleteCustomer(String rawPassword, String token) {

        // jwt 토큰에 저장된 손님 이메일 추출
        String customerEmail = jwtUtil.extractCustomerEmail(token);

        // 추출한 이메일로 손님 조회
        Customer foundCustomer = customerAuthRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

        // 비밀번호 일치 여부 확인
        String encodedPassword = foundCustomer.getPassword();
        boolean isPasswordMisMatching = !bcrypt.matches(rawPassword, encodedPassword);

        if (isPasswordMisMatching) {
            log.info("아이디 또는 비밀번호가 잘못되었습니다.");
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        log.info("손님 {} 탈퇴 완료", customerEmail);

        // 회원 삭제
        customerAuthRepository.updateIsDeletedByEmail(customerEmail, 1);
        LocalDateTime currentTime = LocalDateTime.now();
        customerAuthRepository.updateDeletedAtByEmail(customerEmail, currentTime);

    }
}
