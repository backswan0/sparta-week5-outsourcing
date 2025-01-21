package com.example.outsourcingproject.domain.store.dto.response;

import com.example.outsourcingproject.common.entity.Store;
import java.time.LocalTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CreateStoreResponseDto {

    private final Long id;
    private final String storeName;
    private final String storeAddress;
    private final String storeTelephone;
    private final Integer minimumPurchase;
    private final LocalTime opensAt;
    private final LocalTime closesAt;
    private final List<StoreCategoryResponseDto> categories;

    public CreateStoreResponseDto(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.storeTelephone = store.getStoreTelephone();
        this.minimumPurchase = store.getMinimumPurchase();
        this.opensAt = store.getOpensAt();
        this.closesAt = store.getClosesAt();

        this.categories = store.getMappingStoreCategoryList()
            .stream()
            .map(storeCategory -> new StoreCategoryResponseDto(storeCategory.getStoreCategory()))
            .toList();
    }
}