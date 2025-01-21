package com.example.outsourcingproject.domain.category.dto.response;

import com.example.outsourcingproject.common.entity.MenuCategory;

public class CreateMenuCategoryResponseDto {

    private Long id;
    private String name;

    // 기본 생성자
    public CreateMenuCategoryResponseDto() {
        // 기본 생성자 로직이 없으면 빈 상태로 객체를 생성합니다.
    }

    // MenuCategory를 받아서 초기화하는 생성자
    public CreateMenuCategoryResponseDto(MenuCategory menuCategory) {
        this.id = menuCategory.getId();
        this.name = menuCategory.getName();
    }

    // id와 name을 받아서 초기화하는 생성자
    public CreateMenuCategoryResponseDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter 메서드들 (어노테이션 대신 수동으로 추가)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
