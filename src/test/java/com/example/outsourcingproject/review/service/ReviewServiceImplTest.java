//package com.example.outsourcingproject.review.service;
//
//import static org.hamcrest.Matchers.any;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//
//import com.example.outsourcingproject.auth.repository.CustomerAuthRepository;
//import com.example.outsourcingproject.common.entity.Customer;
//import com.example.outsourcingproject.common.entity.Order;
//import com.example.outsourcingproject.common.entity.Review;
//import com.example.outsourcingproject.common.entity.Store;
//import com.example.outsourcingproject.common.entity.StoreCategory;
//import com.example.outsourcingproject.common.exception.CustomException;
//import com.example.outsourcingproject.common.notfound.exception.OrderNotFoundException;
//import com.example.outsourcingproject.domain.order.OrderState;
//import com.example.outsourcingproject.domain.repository.order.OrderRepository;
//import com.example.outsourcingproject.domain.request.dto.review.CreateReviewRequestDto;
//import com.example.outsourcingproject.domain.response.dto.review.CreateReviewResponseDto;
//import com.example.outsourcingproject.domain.response.dto.review.FindReviewResponseDto;
//import com.example.outsourcingproject.domain.repository.review.ReviewRepository;
//import com.example.outsourcingproject.common.utils.JwtUtil;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class ReviewServiceImplTest {
//
//    @InjectMocks
//    private ReviewServiceImpl reviewService;
//
//    @Mock
//    private ReviewRepository reviewRepository;
//    @Mock
//    private OrderRepository orderRepository;
//    @Mock
//    private CustomerAuthRepository customerAuthRepository;
//    @Mock
//    private JwtUtil jwtUtil;
//
//    // 리뷰 생성 성공 테스트
//    @Test
//    public void createReviewService_성공() {
//        // given
//        Long orderId = 1L;
//        CreateReviewRequestDto requestDto = new CreateReviewRequestDto(
//            "맛있어요!",
//            5
//        );
//
//        // testToken 생성
//        String token = "testToken";
//
//        // test 를 위한 LocalTime 값 설정 (Store 객체 생성 목적)
//        LocalTime testOpensAt = LocalTime.of(
//            10,
//            0
//        );
//        LocalTime testClosesAt = LocalTime.of(
//            21,
//            0
//        );
//
//        // Store 객체 생성 목적: StoreCategory 값 설정
//        StoreCategory testStoreCategoryOne = new StoreCategory("testStoreCategoryOne");
//        StoreCategory testStoreCategoryTwo = new StoreCategory("testStoreCategoryTwo");
//
//        // Customer 객체 생성
//        Customer customer = new Customer(
//            "test@test.com",
//            "testPassword"
//        );
//
//        // Store 객체 생성
//        Store store = new Store(
//            1L,
//            "testStoreName",
//            "testStoreTelephone",
//            "testStoreAddress",
//            100,
//            testOpensAt,
//            testClosesAt,
//            testStoreCategoryOne,
//            testStoreCategoryTwo
//        );
//
//        // Order 객체 생성
//        Order order = new Order(OrderState.DELIVERED, store);
//
//        when(orderRepository
//            .findById(orderId))
//            .thenReturn(Optional.of(order));
//
//        when(jwtUtil.extractCustomerEmail(token))
//            .thenReturn(customer.getEmail());
//
//        when(customerAuthRepository
//            .findByEmail(customer.getEmail()))
//            .thenReturn(Optional.of(customer));
//
//        // Review 객체 생성
//        Review savedReview = new Review(
//            customer,
//            store,
//            order,
//            requestDto.getContents(),
//            requestDto.getRating()
//        );
//
//        // 가짜 reviewId 설정
//        savedReview.setId(1L);
//        // 가짜 createdAt 설정
//        LocalDateTime localDateTime = LocalDateTime.now();
//
//        // CreateReviewResponseDto 객체 생성
//        CreateReviewResponseDto responseDto = new CreateReviewResponseDto(
//            savedReview.getCustomer().getId(),
//            savedReview.getContents(),
//            savedReview.getRating(),
//            localDateTime
//        );
//
//        when(reviewRepository.save(any(Review.class))).thenReturn(responseDto);
//
//        // when
//        CreateReviewResponseDto responseDto = reviewService.createReviewService(orderId, requestDto, token);
//
//        // then
//        assertNotNull(responseDto);
//        assertEquals(requestDto.getContents(), responseDto.getContents());
//        assertEquals(requestDto.getRating(), responseDto.getRating());
//        // 시간 비교 제거 또는 유연한 비교 (예시: null이 아닌지 확인)
//        assertNotNull(responseDto.getCreatedAt());
//    }
//
//    // 주문 조회 실패 (주문 없음) 테스트
//    @Test
//    public void createReviewService_주문_없음_예외() {
//        Long orderId = 1L;
//        CreateReviewRequestDto requestDto = new CreateReviewRequestDto();
//        String token = "testToken";
//        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
//
//        assertThrows(OrderNotFoundException.class,
//            () -> reviewService.createReviewService(orderId, requestDto, token));
//    }
//
//    // 주문 상태가 배송 완료가 아닌 경우 예외 테스트
//    @Test
//    public void createReviewService_주문상태_배송완료_아님_예외() {
//        Long orderId = 1L;
//        CreateReviewRequestDto requestDto = new CreateReviewRequestDto();
//        String token = "testToken";
//        Order order = new Order(orderId, new Customer(), new Store(), OrderState.PAYMENT);
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//
//    }
//
//    @Test
//    void createReviewService_중복_리뷰_예외() {
//        Long orderId = 1L;
//        CreateReviewRequestDto requestDto = new CreateReviewRequestDto();
//        String token = "testToken";
//        Customer customer = new Customer(1L, "test@test.com");
//        Order order = new Order(orderId, customer, new Store(), OrderState.DELIVERED);
//        Review existingReview = new Review();
//
//        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
//        when(jwtUtil.extractCustomerEmail(token)).thenReturn(customer.getEmail());
//        when(customerAuthRepository.findByEmail(customer.getEmail())).thenReturn(
//            Optional.of(customer));
//        when(reviewRepository.findByOrderIdAndCustomerId(orderId, customer.getId())).thenReturn(
//            Optional.of(existingReview));
//
//        assertThrows(
//            CustomException.class,
//            () -> reviewService.createReviewService(orderId, requestDto, token));
//    }
//
//    @Test
//    void findAllReviewService_정상적인_조회_최신순() {
//        // Given
//        Long storeId = 1L;
//        String sort = "latest";
//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰1", 5));
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰2", 4));
//        when(reviewRepository.findByStoreId(storeId)).thenReturn(reviews);
//
//        // When
//        List<FindReviewResponseDto> responseDtos = reviewService.findAllReviewService(storeId, sort,
//            null, null);
//
//        // Then
//        assertEquals(2, responseDtos.size());
//        // 최신순 검증 로직 추가 (createdAt 비교)
//    }
//
//    @Test
//    void findAllReviewService_정상적인_조회_오래된순() {
//        // Given
//        Long storeId = 1L;
//        String sort = "oldest";
//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰1", 5));
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰2", 4));
//        when(reviewRepository.findByStoreId(storeId)).thenReturn(reviews);
//
//        // When
//        List<FindReviewResponseDto> responseDtos = reviewService.findAllReviewService(storeId, sort,
//            null, null);
//
//        // Then
//        assertEquals(2, responseDtos.size());
//        // 오래된 순 검증 로직 추가 (createdAt 비교)
//    }
//
//    @Test
//    void findAllReviewService_리뷰_없음_예외() {
//        Long storeId = 1L;
//        when(reviewRepository.findByStoreId(storeId)).thenReturn(new ArrayList<>());
//
//        assertThrows(CustomException.class,
//            () -> reviewService.findAllReviewService(storeId, "latest", null, null));
//    }
//
//    //별점 필터 테스트 추가
//    @Test
//    void findAllReviewService_별점_범위_필터링() {
//        Long storeId = 1L;
//        Integer startRating = 3;
//        Integer endRating = 4;
//        String sort = "latest";
//
//        List<Review> reviews = new ArrayList<>();
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰1", 3));
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰2", 4));
//        reviews.add(new Review(new Customer(), new Store(), new Order(), "리뷰3", 5)); // 필터링 되어야 함
//
//        when(reviewRepository.findByStoreIdAndRatingBetween(storeId, startRating,
//            endRating)).thenReturn(
//            reviews.stream().filter(r -> r.getRating() >= startRating && r.getRating() <= endRating)
//                .toList());
//
//        List<FindReviewResponseDto> responseDtos = reviewService.findAllReviewService(storeId, sort,
//            startRating, endRating);
//
//        assertEquals(2, responseDtos.size());
//        assertEquals(3, responseDtos.get(0).getRating());
//        assertEquals(4, responseDtos.get(1).getRating());
//    }
//}