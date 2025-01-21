package com.example.outsourcingproject.domain.category.service;

import com.example.outsourcingproject.common.entity.MenuCategory;
import com.example.outsourcingproject.domain.category.dto.request.CreateMenuCategoryRequestDto;
import com.example.outsourcingproject.domain.category.dto.response.CreateMenuCategoryResponseDto;
import com.example.outsourcingproject.domain.category.repository.MenuCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional
    public CreateMenuCategoryResponseDto createMenuCategory(
        CreateMenuCategoryRequestDto requestDto
    ) {
        MenuCategory menuCategoryToSave = new MenuCategory(requestDto.getName());

        MenuCategory savedMenuCategory = menuCategoryRepository.save(menuCategoryToSave);

        return new CreateMenuCategoryResponseDto(savedMenuCategory);

    }
}
