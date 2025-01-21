package com.example.outsourcingproject.domain.category.controller;

import com.example.outsourcingproject.category.controller.MenuCategoryController;
import com.example.outsourcingproject.category.dto.request.CreateMenuCategoryRequestDto;
import com.example.outsourcingproject.category.dto.response.CreateMenuCategoryResponseDto;
import com.example.outsourcingproject.category.service.MenuCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MenuStoreCategoryControllerTest {

    private static final String BASE_URL = "/menu-categories";

    private MockMvc mockMvc;

    @Mock
    private MenuCategoryService menuCategoryService;

    @InjectMocks
    private MenuCategoryController menuCategoryController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private CreateMenuCategoryRequestDto requestDto;
    private CreateMenuCategoryResponseDto responseDto;

    @BeforeEach
    void setUp() {
        // MockMvc 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(menuCategoryController).build();

        // 테스트 데이터 준비
        requestDto = new CreateMenuCategoryRequestDto("한식");
        responseDto = new CreateMenuCategoryResponseDto(1L, "한식");
    }

    @Test
    void 메뉴카테고리_생성_컨트롤러_단위_테스트() throws Exception {
        // given
        when(menuCategoryService.createMenuCategory(any(CreateMenuCategoryRequestDto.class)))
            .thenReturn(responseDto);

        // when
        String contentAsString = this.mockMvc.perform(
                post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))  // 요청 DTO를 JSON으로 변환
            .andExpect(status().isCreated())  // 응답 상태가 201 Created인지 검증
            .andReturn()
            .getResponse()
            .getContentAsString();  // 응답을 String으로 받음

        // then: 응답 DTO 필드별로 검증
        CreateMenuCategoryResponseDto actualResult = objectMapper.readValue(contentAsString, CreateMenuCategoryResponseDto.class);

        // 필드 값 비교
        assertThat(actualResult.getId()).isEqualTo(responseDto.getId());
        assertThat(actualResult.getName()).isEqualTo(responseDto.getName());

        // 서비스 메서드 호출 여부 확인
        verify(menuCategoryService).createMenuCategory(any(CreateMenuCategoryRequestDto.class));
    }
}
