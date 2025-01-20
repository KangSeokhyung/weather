package com.weather.dto;

import lombok.Data;

@Data
public class WeatherApiResponse {
    private String city;
    private Double temperature;
    private Double highTemp;
    private Double lowTemp;
    private String weather;
    private Integer humidity;
    private Double windSpeed;
}
