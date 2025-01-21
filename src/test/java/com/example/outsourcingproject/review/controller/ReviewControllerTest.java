//package com.example.outsourcingproject.review.controller;
//
//import com.example.outsourcingproject.domain.request.dto.review.CreateReviewRequestDto;
//import com.example.outsourcingproject.domain.response.dto.review.CreateReviewResponseDto;
//import com.example.outsourcingproject.domain.service.review.ReviewServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Import(ReviewControllerTest.TestConfig.class) // TestConfig 임포트
//public class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper; // ObjectMapper 주입
//
//    @Mock // Mock 객체 생성
//    private ReviewServiceImpl reviewServiceImpl;
//
//    @Test
//    public void createReview_성공() throws Exception {
//        // 테스트 데이터 준비
//        CreateReviewRequestDto requestDto = new CreateReviewRequestDto("review content", 5);
//        CreateReviewResponseDto responseDto = new CreateReviewResponseDto(1L, "review content", 5, LocalDateTime.now());
//
//        // 서비스 메서드 스텁 설정
//        when(reviewServiceImpl.createReviewService(eq(1L), any(CreateReviewRequestDto.class), eq("Bearer testToken")))
//            .thenReturn(responseDto);
//
//        // 요청 수행 및 결과 검증
//        mockMvc.perform(post("/orders/1/reviews")
//                .header("Authorization", "Bearer testToken")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(requestDto))) // ObjectMapper 사용
//            .andExpect(status().isCreated());
//    }
//
//    @TestConfiguration // 테스트 전용 설정 클래스
//    static class TestConfig {
//        @Bean
//        public ReviewServiceImpl reviewService() {
//            return Mockito.mock(ReviewServiceImpl.class);
//        }
//    }
//}