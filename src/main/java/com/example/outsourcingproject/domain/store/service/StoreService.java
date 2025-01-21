package com.example.outsourcingproject.domain.store.service;

import com.example.outsourcingproject.domain.store.dto.request.CreateStoreRequestDto;
import com.example.outsourcingproject.domain.store.dto.request.UpdateStoreRequestDto;
import com.example.outsourcingproject.domain.store.dto.response.CreateStoreResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.StoreCategorySearchResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.StoreNameSearchResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.UpdateStoreResponseDto;
import java.util.List;

public interface StoreService {

    CreateStoreResponseDto createStore(
        CreateStoreRequestDto requestDto,
        String token
    );

    List<StoreNameSearchResponseDto> readAllStoresByStoreName(String storeName);

    List<StoreCategorySearchResponseDto> readAllStoresByStoreCategory(String storeCategoryName);

    StoreResponseDto findStoreByStoreId(Long storeId);

    UpdateStoreResponseDto updateStore(Long id, UpdateStoreRequestDto requestDto);

    void deleteStore(Long storeId, String token);
}
