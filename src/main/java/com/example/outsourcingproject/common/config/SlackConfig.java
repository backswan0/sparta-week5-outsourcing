package com.example.outsourcingproject.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SlackConfig {

    // RestTemplate로 슬랙 메시지 전송 api 요청하기 위해 빈 설정
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
