package com.weather.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling      // 스케줄링 기능 활성화
public class BatchConfig {
    // Spring Boot 3.x에서는 대부분의 배치 설정이 자동으로 구성됩니다.
}
