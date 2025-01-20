package com.weather.repository;

import com.weather.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

/**
 * 날씨 데이터 Repository 인터페이스
 * JpaRepository를 상속받아 기본적인 CRUD 기능 사용 가능
 */
public interface WeatherRepository extends JpaRepository<WeatherData, LocalDate> {
    
    /**
     * 특정 기간의 날씨 데이터를 조회하는 메서드
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 해당 기간의 날씨 데이터 리스트
     */
    @Query("SELECT w FROM WeatherData w WHERE w.weatherDate BETWEEN :startDate AND :endDate")
    List<WeatherData> findByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
