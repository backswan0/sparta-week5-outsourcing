package com.example.outsourcingproject.domain.store.controller;

import com.example.outsourcingproject.common.aspect.AuthCheck;
import com.example.outsourcingproject.domain.store.dto.request.CreateStoreRequestDto;
import com.example.outsourcingproject.domain.store.dto.request.UpdateStoreRequestDto;
import com.example.outsourcingproject.domain.store.dto.response.CreateStoreResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.StoreCategorySearchResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.StoreNameSearchResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import com.example.outsourcingproject.domain.store.dto.response.UpdateStoreResponseDto;
import com.example.outsourcingproject.domain.store.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @AuthCheck("OWNER")
    @PostMapping
    public ResponseEntity<CreateStoreResponseDto> createStore(
        @RequestBody CreateStoreRequestDto requestDto,
        @RequestHeader("Authorization") String token) { //todo @Valid 유효성 검사

        CreateStoreResponseDto responseDto = storeService.createStore(
            requestDto,
            token
        );

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StoreNameSearchResponseDto>> readAllStoresByStoreName(
        @RequestParam String search
    ) {
        List<StoreNameSearchResponseDto> responseDtoList = new ArrayList<>();

        responseDtoList = storeService.readAllStoresByStoreName(search);

        log.info("findAllStoresByStoreName:{}", responseDtoList);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<StoreCategorySearchResponseDto>> readAllStoresByStoreCategory(
        @RequestParam String search
    ) {
        List<StoreCategorySearchResponseDto> responseDtoList = new ArrayList<>();

        responseDtoList = storeService.readAllStoresByStoreCategory(search);

        log.info("findAllStoresByStoreCategory: {}", responseDtoList);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> readOneStore(
        @PathVariable("storeId") Long storeId
    ) {
        StoreResponseDto responseDto = storeService.findStoreByStoreId(storeId);

        log.info(responseDto.getStoreName());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 가게 수정
    @AuthCheck("OWNER")
    @PatchMapping("/{storeId}")
    public ResponseEntity<UpdateStoreResponseDto> updateStore(
        @PathVariable("storeId") Long storeId,
        @RequestBody UpdateStoreRequestDto requestDto //todo @Valid 유효성 검사
    ) {
        UpdateStoreResponseDto responseDto = storeService.updateStore(storeId, requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 가게 폐업
    @AuthCheck("OWNER")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(
        @PathVariable Long storeId,
        HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        storeService.deleteStore(storeId, token);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

