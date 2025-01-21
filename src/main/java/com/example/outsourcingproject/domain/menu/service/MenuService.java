package com.example.outsourcingproject.domain.menu.service;

import com.example.outsourcingproject.domain.menu.dto.request.CreateMenuRequestDto;
import com.example.outsourcingproject.domain.menu.dto.request.UpdateMenuRequestDto;
import com.example.outsourcingproject.domain.menu.dto.response.CreateMenuResponseDto;
import com.example.outsourcingproject.domain.menu.dto.response.UpdateMenuResponseDto;

public interface MenuService {

    CreateMenuResponseDto createMenu(
        Long storeId,
        CreateMenuRequestDto requestDto
    );

    UpdateMenuResponseDto updateMenu(
        Long storeId,
        Long menuId,
        UpdateMenuRequestDto requestDto
    );


    void deleteMenu(Long menuId);
}
