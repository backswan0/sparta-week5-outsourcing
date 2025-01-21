package com.example.outsourcingproject.domain.store.dto.response;


import com.example.outsourcingproject.common.entity.Store;
import com.example.outsourcingproject.domain.store.dto.MenuDto;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class StoreResponseDto {

    private final String storeName;
    private final String storeAddress;
    private final String storeTelephone;
    private final Integer minimumPurchase;
    private final LocalTime opensAt;
    private final LocalTime closesAt;
    private List<MenuDto> menuList = new ArrayList<>();

    public StoreResponseDto(
        Store store,
        List<MenuDto> menuList
    ) {
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.storeTelephone = store.getStoreTelephone();
        this.minimumPurchase = store.getMinimumPurchase();
        this.opensAt = store.getOpensAt();
        this.closesAt = store.getClosesAt();
        this.menuList = menuList;
    }
}
