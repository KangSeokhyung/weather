package com.weather.service;

import com.weather.entity.WeatherData;
import com.weather.repository.WeatherRepository;
import com.weather.dto.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * 날씨 데이터 서비스 구현 클래스
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String apiUrl;

    @Autowired
    public WeatherServiceImpl(
            WeatherRepository weatherRepository,
            RestTemplate restTemplate,
            @Value("${weather.api.key:dummykey}") String apiKey,
            @Value("${weather.api.url:https://api.openweathermap.org/data/2.5/weather}") String apiUrl) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<WeatherData> getWeatherDataBetween(LocalDate startDate, LocalDate endDate) {
        return weatherRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<WeatherData> getMonthlyWeatherData(String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4));
        
        YearMonth ym = YearMonth.of(year, month);
        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();
        
        return getWeatherDataBetween(startDate, endDate);
    }

    @Override
    public WeatherApiResponse getWeatherAPI(String city) {
        try {
            String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
            return restTemplate.getForObject(url, WeatherApiResponse.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch weather data for city: " + city, e);
        }
    }
}
