package com.weather.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WeatherApiRequest {
    private String serviceKey;  // 인증키
    private int numOfRows;      // 한 페이지 결과 수
    private int pageNo;         // 페이지 번호
    private String dataType;    // 응답자료형식
    private String dataCd;      // 자료 코드
    private String dateCd;      // 날짜 코드
    private String startDt;     // 시작일
    private String endDt;       // 종료일
    private String stnIds;      // 지점 번호

    public static WeatherApiRequest getDefaultRequest(String serviceKey) {
        return WeatherApiRequest.builder()
                .serviceKey(serviceKey)
                .numOfRows(10)
                .pageNo(1)
                .dataType("JSON")
                .dataCd("ASOS")
                .dateCd("DAY")
                .build();
    }
}
