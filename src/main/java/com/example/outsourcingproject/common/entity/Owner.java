package com.example.outsourcingproject.common.entity;

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
@Table(name = "owners")
public class Owner extends BaseEntity {

    @Comment("사장님 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("사장님 이메일")
    @Column(
        name = "email",
        nullable = false
    )
    private String email;

    @Comment("사장님 비밀번호")
    @Column(
        name = "password",
        nullable = false
    )
    private String password;

    // Customer 테이블에 저장된 데이터의 authority는 무조건 사장님
    // 손님으로 권한 수정? -> 사장님 데이터 삭제(탈퇴) 후 손님 테이블에 데이터 생성(회원가입) 하기
    @Enumerated(EnumType.STRING)
    @Column(
        name = "authority",
        updatable = false,
        nullable = false
    )
    private Authority authority;

    protected Owner() {
    }

    public Owner(
        String email,
        String password
    ) {
        this.email = email;
        this.password = password;
        this.authority = Authority.OWNER;
    }
}
