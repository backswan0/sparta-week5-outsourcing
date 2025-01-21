package com.example.outsourcingproject.auth.service;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.auth.dto.request.SignInOwnerRequestDto;
import com.example.outsourcingproject.auth.dto.request.SignUpOwnerRequestDto;
import com.example.outsourcingproject.auth.dto.response.SignInOwnerResponseDto;
import com.example.outsourcingproject.auth.dto.response.SignUpOwnerResponseDto;
import com.example.outsourcingproject.auth.repository.OwnerAuthRepository;
import com.example.outsourcingproject.common.entity.Owner;
import com.example.outsourcingproject.common.exception.CustomException;
import com.example.outsourcingproject.common.exception.ErrorCode;
import com.example.outsourcingproject.common.utils.JwtUtil;
import com.example.outsourcingproject.common.utils.PasswordEncoder;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerAuthServiceImpl implements OwnerAuthService {

    private final OwnerAuthRepository ownerAuthRepository;
    private final JwtUtil jwtUtil;
    PasswordEncoder bcrypt = new PasswordEncoder();

    @Override
    public SignUpOwnerResponseDto signUp(SignUpOwnerRequestDto requestDto) {

        // 등록된 아이디(이메일) 여부 확인
        boolean isExistEmail = ownerAuthRepository.findByEmail(requestDto.getEmail())
            .isPresent();

        if (isExistEmail) {
            log.info("이미 존재하는 이메일입니다. >> {}", requestDto.getEmail());
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        }
        Owner ownerToSave = new Owner(
            requestDto.getEmail(),
            bcrypt.encode(requestDto.getPassword())
        );

        Owner savedOwner = ownerAuthRepository.save(ownerToSave);

        log.info("사장님 {} 회원가입 완료", requestDto.getEmail());
        return new SignUpOwnerResponseDto(savedOwner);
    }

    @Override
    public SignInOwnerResponseDto signIn(SignInOwnerRequestDto requestDto) {

        // 탈퇴하지 않은 사장님들 중에서 이메일 값이 일치하는 사장님 추출
        Owner foundOwner = ownerAuthRepository.findByEmailAndIsDeletedFalse(requestDto.getEmail())
            .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

        String encodedPassword = foundOwner.getPassword();

        boolean isPasswordMismatching = !bcrypt.matches(
            requestDto.getPassword(),
            encodedPassword
        );

        if (isPasswordMismatching) {
            log.info("아이디 또는 비밀번호가 잘못되었습니다.");
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        log.info("사장님 {} 로그인 완료", requestDto.getEmail());

        String token = jwtUtil.createToken(
            requestDto.getEmail(),
            foundOwner.getAuthority()
        );

        // 앞의 7글자 ('Bearer ')를 제외한 실제 토큰 부분만 추출
        String actualToken = token.substring(7);

        return new SignInOwnerResponseDto(actualToken);
    }

    @AuthCheck("OWNER")
    @Override
    public void deleteOwner(String rawPassword, String token) {
        // jwt 토큰에 저장된 사장님 이메일 추출
        String ownerEmail = jwtUtil.extractOwnerEmail(token);

        // 추출한 이메일로 사장님 조회
        Owner owner = ownerAuthRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

        // 비밀번호 일치 여부 확인
        boolean isPasswordMismatching = !bcrypt.matches(
            rawPassword,
            owner.getPassword()
        );

        if (isPasswordMismatching) {
            log.info("아이디 또는 비밀번호가 잘못되었습니다.");
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        log.info("손님 {} 탈퇴 완료", ownerEmail);

        // 회원 삭제
        ownerAuthRepository.updateIsDeletedByEmail(ownerEmail, 1);
        LocalDateTime currentTime = LocalDateTime.now();
        ownerAuthRepository.updateDeletedAtByEmail(ownerEmail, currentTime);

    }
}