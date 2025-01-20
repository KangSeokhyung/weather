package com.weather.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 날씨 데이터 Entity 클래스
 * TWEATHER 테이블과 매핑됨
 */
@Data  // Lombok: Getter, Setter, toString 등 자동 생성
@Entity  // JPA: 이 클래스를 Entity로 지정
@Table(name = "TWEATHER")  // JPA: 매핑될 테이블 이름 지정
public class WeatherData {
    @Id  // JPA: Primary Key 지정
    private LocalDate weatherDate;    // 날씨 날짜 (PK)
    private Double highTemp;          // 최고 기온
    private Double lowTemp;           // 최저 기온
    private String weather;           // 날씨 상태
    private LocalDateTime modifyDate; // 수정 일시
    private String modifyId;          // 수정자 ID
}
