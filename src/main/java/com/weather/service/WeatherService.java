package com.weather.service;

import com.weather.entity.WeatherData;
import com.weather.dto.WeatherApiResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * 날씨 데이터 서비스 인터페이스
 * 비즈니스 로직을 처리하는 메서드들을 정의
 */
public interface WeatherService {
    
    /**
     * 특정 기간의 날씨 데이터를 조회
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 해당 기간의 날씨 데이터 리스트
     */
    List<WeatherData> getWeatherDataBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 특정 년월의 날씨 데이터를 조회
     * @param yearMonth 년월 (yyyyMM 형식)
     * @return 해당 월의 날씨 데이터 리스트
     */
    List<WeatherData> getMonthlyWeatherData(String yearMonth);

    /**
     * 외부 날씨 API를 호출하여 특정 도시의 날씨 정보를 조회
     * @param city 도시 이름
     * @return 날씨 정보
     */
    WeatherApiResponse getWeatherAPI(String city);
}
