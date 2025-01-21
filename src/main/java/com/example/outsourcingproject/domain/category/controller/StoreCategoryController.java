package com.example.outsourcingproject.domain.category.controller;

import com.example.outsourcingproject.domain.category.dto.request.CreateStoreCategoryRequestDto;
import com.example.outsourcingproject.domain.category.dto.response.CreateStoreCategoryResponseDto;
import com.example.outsourcingproject.domain.category.service.StoreCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store-categories")
public class StoreCategoryController {
    private final StoreCategoryService storeCategoryService;

    public StoreCategoryController(StoreCategoryService storeCategoryService) {
        this.storeCategoryService = storeCategoryService;
    }

    @PostMapping
    public ResponseEntity<CreateStoreCategoryResponseDto> createStoreCategory(
        @RequestBody CreateStoreCategoryRequestDto requestDto) {
        CreateStoreCategoryResponseDto responseDto = storeCategoryService.createCategory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}