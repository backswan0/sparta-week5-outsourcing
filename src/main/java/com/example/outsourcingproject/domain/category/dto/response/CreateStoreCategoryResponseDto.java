package com.example.outsourcingproject.domain.category.dto.response;


public class CreateStoreCategoryResponseDto {
    private Long id;
    private String name;
    // 기본 생성자 추가
    public CreateStoreCategoryResponseDto() {
    }
    // 생성자
    public CreateStoreCategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    // id에 대한 getter
    public Long getId() {
        return id;
    }
    // name에 대한 getter
    public String getName() {
        return name;
    }
}