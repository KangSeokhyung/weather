package com.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WeatherApiResponse {
    @JsonProperty("response")
    private Response response;

    @Getter
    @Setter
    public static class Response {
        @JsonProperty("header")
        private Header header;
        @JsonProperty("body")
        private Body body;
    }

    @Getter
    @Setter
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        @JsonProperty("dataType")
        private String dataType;
        @JsonProperty("numOfRows")
        private int numOfRows;
        @JsonProperty("pageNo")
        private int pageNo;
        @JsonProperty("totalCount")
        private int totalCount;
        @JsonProperty("items")
        private Items items;
    }

    @Getter
    @Setter
    public static class Items {
        @JsonProperty("item")
        private List<WeatherItem> item;
    }

    @Getter
    @Setter
    public static class WeatherItem {
        @JsonProperty("stnId")
        private String stnId;        // 지점 번호
        @JsonProperty("stnNm")
        private String stnNm;        // 지점명
        @JsonProperty("tm")
        private String tm;           // 시간
        @JsonProperty("avgTa")
        private Double avgTa;        // 평균 기온
        @JsonProperty("minTa")
        private Double minTa;        // 최저 기온
        @JsonProperty("minTaHrmt")
        private String minTaHrmt;    // 최저 기온 시각
        @JsonProperty("maxTa")
        private Double maxTa;        // 최고 기온
        @JsonProperty("maxTaHrmt")
        private String maxTaHrmt;    // 최고 기온 시각
        @JsonProperty("sumRnDur")
        private Double sumRnDur;     // 강수 계속시간
        @JsonProperty("sumRn")
        private Double sumRn;        // 일강수량
        @JsonProperty("avgWs")
        private Double avgWs;        // 평균 풍속
        @JsonProperty("avgRhm")
        private Double avgRhm;       // 평균 상대습도
        @JsonProperty("avgPa")
        private Double avgPa;        // 평균 현지기압
    }
}
