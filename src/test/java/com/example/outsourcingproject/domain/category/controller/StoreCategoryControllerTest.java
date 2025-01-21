package com.example.outsourcingproject.domain.category.controller;
import com.example.outsourcingproject.category.controller.StoreCategoryController;
import com.example.outsourcingproject.category.dto.request.CreateStoreCategoryRequestDto;
import com.example.outsourcingproject.category.dto.response.CreateStoreCategoryResponseDto;
import com.example.outsourcingproject.category.service.StoreCategoryService;
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
public class StoreCategoryControllerTest {
    private static final String BASE_URL = "/store-categories";
    private MockMvc mockMvc;
    @Mock
    private StoreCategoryService storeCategoryService;
    @InjectMocks
    private StoreCategoryController storeCategoryController;
    private ObjectMapper objectMapper = new ObjectMapper();
    private CreateStoreCategoryRequestDto requestDto;
    private CreateStoreCategoryResponseDto responseDto;
    @BeforeEach
    void setUp() {
        // MockMvc 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(storeCategoryController).build();
        // 테스트 데이터 준비
        requestDto = new CreateStoreCategoryRequestDto("음식점");
        // responseDto 설정
        responseDto = new CreateStoreCategoryResponseDto(1L, "음식점");
    }
    @Test
    void 스토어카테고리_생성_컨트롤러_단위_테스트() throws Exception {
        // given
        when(storeCategoryService.createCategory(any(CreateStoreCategoryRequestDto.class)))
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
        // 응답 내용 출력 (디버깅용)
        System.out.println("Response Content: " + contentAsString);
        // then: 응답 DTO 필드별로 검증
        CreateStoreCategoryResponseDto actualResult = objectMapper.readValue(contentAsString, CreateStoreCategoryResponseDto.class);
        // 필드 값 비교
        assertThat(actualResult.getId()).isEqualTo(responseDto.getId());
        assertThat(actualResult.getName()).isEqualTo(responseDto.getName());
        // 서비스 메서드 호출 여부 확인
        verify(storeCategoryService).createCategory(any(CreateStoreCategoryRequestDto.class));
    }
}