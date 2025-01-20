package com.weather.controller;

import com.weather.entity.WeatherData;
import com.weather.dto.WeatherApiResponse;
import com.weather.service.WeatherService;
import com.weather.service.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.List;

/**
 * 날씨 데이터 컨트롤러
 * 캘린더 페이지와 날씨 데이터 API를 처리
 */
@Controller
public class WeatherController {
    
    private final WeatherService weatherService;
    private final WeatherApiService weatherApiService;

    @Autowired
    public WeatherController(WeatherService weatherService, WeatherApiService weatherApiService) {
        this.weatherService = weatherService;
        this.weatherApiService = weatherApiService;
    }

    /**
     * 캘린더 페이지를 반환
     */
    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }

    /**
     * 월별 날씨 데이터를 반환
     * @param yearMonth 년월 (yyyyMM 형식)
     * @return 해당 월의 날씨 데이터 리스트
     */
    @GetMapping("/api/weather/monthly/{yearMonth}")
    @ResponseBody
    public List<WeatherData> getMonthlyWeather(@PathVariable String yearMonth) {
        return weatherService.getMonthlyWeatherData(yearMonth);
    }

    /**
     * 도시의 현재 날씨 정보를 반환
     * @param city 도시 이름
     * @return 현재 날씨 정보
     */
    @GetMapping("/api/weather/city/{city}")
    @ResponseBody
    public WeatherApiResponse getWeatherAPI(@PathVariable String city) {
        return weatherService.getWeatherAPI(city);
    }

    /**
     * 기상청 API를 통해 날씨 데이터를 조회
     */
    @GetMapping("/api/weather")
    @ResponseBody
    public WeatherApiResponse getWeatherData(
            @RequestParam String startDt,
            @RequestParam String endDt,
            @RequestParam String stnIds) {
        return weatherApiService.getWeatherData(startDt, endDt, stnIds);
    }
}
