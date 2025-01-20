package com.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WeatherApiResponse {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        private String dataType;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
        private List<WeatherItem> items;
    }

    @Getter
    @Setter
    public static class WeatherItem {
        private String stnId;        // 지점 번호
        private String stnNm;        // 지점명
        private String tm;           // 시간
        private Double avgTa;        // 평균 기온
        private Double minTa;        // 최저 기온
        private String minTaHrmt;    // 최저 기온 시각
        private Double maxTa;        // 최고 기온
        private String maxTaHrmt;    // 최고 기온 시각
        private Double sumRnDur;     // 강수 계속시간
        private Double sumRn;        // 일강수량
        private Double avgWs;        // 평균 풍속
        private Double avgRhm;       // 평균 상대습도
        private Double avgPa;        // 평균 현지기압
    }
}
