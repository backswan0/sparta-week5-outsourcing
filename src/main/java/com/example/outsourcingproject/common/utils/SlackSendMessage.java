package com.example.outsourcingproject.common.utils;

import com.example.outsourcingproject.domain.order.OrderState;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSendMessage {

    private final RestTemplate restTemplate; // SlackConfig로 빈 설정

    // @Value -> application.properties에 저장한 값을 이용한다.
    @Value("${slack.api.url}")
    String requestURL;

    @Value("${slack.bot.token}")
    String botToken;

    @Value("${slack.channel.id}")
    String channelId;

    /**
     * 주문 생성 및 상태 변경 시 호출되는 메서드 - 예외 처리 및 sendMessage()를 감싼다.
     *
     * @param storeName
     * @param orderState
     */
    public void callSlackSendMessageApi(String storeName, OrderState orderState) {
        String content = null;
        try {
            switch (orderState) {
                case PENDING -> content = "[" + storeName + "] " + "가게에 주문이 전송되었습니다.";
                case ACCEPTED -> content = "[" + storeName + "] " + "가게에서 주문을 수락했습니다.";
                case CANCELED -> content = "[" + storeName + "] " + "가게의 요청으로 주문이 취소되었습니다.";
                case DELIVERING -> content = "[" + storeName + "] " + "배달이 출발되었습니다.";
                case DELIVERED -> content = "[" + storeName + "] " + "배달이 완료되었습니다.";
            }
            sendMessage(content);
        } catch (Exception e) {
            // 슬랙 메시지 전송이 오류가 프로그램 실행에 영향가지 않도록 예외처리 throw를 하지 않고, log만 기록
            log.error("callSlackSendMessageApi error: " + e.toString());
        }
    }

    /**
     * 실제 Slack API 호출한다.
     *
     * @param content
     * @throws IOException
     */
    public void sendMessage(String content) throws IOException {

        // Http header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(botToken);

        // 요청 바디
        String requestBody = String.format(
            "{\"channel\":\"%s\", \"text\":\"%s\"}",
            channelId,
            content
        );

        // Http 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 슬랙 메시지 전송 API 요청
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                requestURL,
                HttpMethod.POST,
                requestEntity,
                String.class
            );

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                if (response.getBody() != null && response.getBody().contains("\"ok\":true")) {
                    log.info("Slack 메시지 전송 성공 : {}", content);
                } else {
                    log.error("Slack 메시지 전송 실패 : {}", response.getBody());
                }
            } else {
                log.error(
                    "Slack API 오류: Status Code = {}, Response = {} ",
                    response.getStatusCode(), response.getBody()
                );
            }
        } catch (Exception e) {
            log.error(
                "Slack 메시지 요청 중 예외가 발생함 : {}",
                e.getMessage(),
                e
            );
        }
    }

}
