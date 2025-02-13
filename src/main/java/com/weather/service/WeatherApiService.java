package com.weather.service;

import com.weather.dto.WeatherApiResponse;
import com.weather.entity.WeatherData;
import com.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherApiService {
    
    @Value("${weather.api.key}")
    private String apiKey;
    
    @Value("${weather.api.url}")
    private String apiUrl;
    
    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;

    @Transactional
    public WeatherApiResponse getWeatherData(String startDt, String endDt, String stnIds) {
        try {
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

            log.info("Calling weather API with URL: {}", url);
            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
            log.info("API Response: {}", response);
            
            if (response != null && response.getResponse() != null && 
                response.getResponse().getBody() != null && 
                response.getResponse().getBody().getItems() != null &&
                response.getResponse().getBody().getItems().getItem() != null) {
                
                LocalDate date = LocalDate.parse(startDt, DateTimeFormatter.BASIC_ISO_DATE);
                
                // 이미 해당 날짜의 데이터가 있는지 확인
                if (!weatherRepository.existsByWeatherDate(date)) {
                    log.info("No existing data found for date: {}. Saving {} items.", 
                            startDt, response.getResponse().getBody().getItems().getItem().size());
                            
                    response.getResponse().getBody().getItems().getItem().forEach(item -> {
                        WeatherData weatherData = new WeatherData();
                        weatherData.setWeatherDate(date);
                        weatherData.setHighTemp(item.getMaxTa());
                        weatherData.setLowTemp(item.getMinTa());
                        weatherData.setWeather(""); // API에서 날씨 상태 정보가 없음
                        weatherData.setModifyDate(LocalDateTime.now());
                        weatherData.setModifyId("BATCH");
                        
                        log.info("Saving weather data: {}", weatherData);
                        weatherRepository.save(weatherData);
                        log.info("Weather data saved for date: {}", startDt);
                    });
                } else {
                    log.info("Weather data already exists for date: {}", startDt);
                }
            } else {
                log.warn("Invalid API response structure: {}", response);
            }
            
            return response;
        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage(), e);
            throw new RuntimeException("날씨 데이터 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    public WeatherApiResponse callWeatherApi(String startDt, String endDt, String stnIds) {
        return getWeatherData(startDt, endDt, stnIds);
    }
}
