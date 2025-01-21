package com.example.outsourcingproject.common.config;

import com.example.outsourcingproject.auth.filter.JwtFilter;
import com.example.outsourcingproject.common.utils.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        // Filter 등록
        filterRegistrationBean.setFilter(new JwtFilter(jwtUtil));
        // Filter 순서 결정
        filterRegistrationBean.setOrder(1);
        // 전체 URL에 Filter 적용
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;

    }

}
