package com.example.outsourcingproject.entity;

import com.example.outsourcingproject.common.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Comment("손님 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("손님 이메일")
    @Column(
        name = "email",
        nullable = false
    )
    private String email;

    @Comment("손님 비밀번호")
    @Column(
        name = "password",
        nullable = false
    )
    private String password;

    // Customer 테이블에 저장된 데이터의 authority는 무조건 손님
    // 사장으로 권한 수정? -> 손님 데이터 삭제 (탈퇴) 후 사장님 테이블에 데이터 생성 (회원가입) 하기
    @Comment("손님 권한")
    @Enumerated(EnumType.STRING)
    @Column(
        name = "authority",
        updatable = false,
        nullable = false
    )
    private Authority authority;

    protected Customer() {
    }

    public Customer(
        String email,
        String password
    ) {
        this.email = email;
        this.password = password;
        this.authority = Authority.CUSTOMER;
    }
}
