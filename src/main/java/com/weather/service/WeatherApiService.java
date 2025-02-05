package com.weather.service;

import com.weather.dto.WeatherApiRequest;
import com.weather.dto.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherApiService {
    
    @Value("${weather.api.key}")
    private String apiKey;
    
    @Value("${weather.api.url}")
    private String apiUrl;
    
    private final RestTemplate restTemplate;
    
    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public WeatherApiResponse getWeatherData(String startDt, String endDt, String stnIds) {
        try {
            //String encodedKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8.toString());\

            /*URI uri = URI.create(apiUrl + "/getWthrDataList" +
                    "?ServiceKey=" + apiKey +
                    "&dataType=JSON&dataCd=ASOS&dateCd=DAY" +
                    "&startDt=" + startDt +
                    "&endDt=" + endDt +
                    "&stnIds=" + stnIds);*/

            String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/getWthrDataList")
                    .queryParam("serviceKey", apiKey)
                    .queryParam("numOfRows", "10")
                    .queryParam("pageNo", "1")
                    .queryParam("dataType", "JSON")
                    .queryParam("dataCd", "ASOS")
                    .queryParam("dateCd", "DAY")
                    .queryParam("startDt", startDt)
                    .queryParam("endDt", endDt)
                    .queryParam("stnIds", stnIds)
                    .build()
                    .toUriString();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("API Response Status: " + response.getStatusCode());
            System.out.println("API Response Body: " + response.getBody());

            return restTemplate.getForObject(url, WeatherApiResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("날씨 데이터 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    public WeatherApiResponse callWeatherApi(String startDt, String endDt, String stnIds) {
        return getWeatherData(startDt, endDt, stnIds);
    }
}
