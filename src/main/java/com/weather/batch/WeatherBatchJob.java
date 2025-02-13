package com.weather.batch;

import com.weather.service.WeatherApiService;
import com.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherBatchJob {

    private final WeatherApiService weatherApiService;
    private final WeatherRepository weatherRepository;

    /**
     * 날씨 데이터를 10초마다 가져오는 배치 작업
     * 2달 전부터 어제까지의 데이터 중 DB에 없는 데이터만 조회
     */
    @Scheduled(fixedDelayString = "10000")
    public void fetchWeatherData() {
        log.info("Weather batch job started");
        
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate twoMonthsAgo = yesterday.minusMonths(2);
        
        log.info("Fetching weather data from {} to {}", twoMonthsAgo, yesterday);
        
        try {
            // DB에 저장된 날짜들 조회
            Set<LocalDate> existingDates = weatherRepository.findExistingDates(twoMonthsAgo, yesterday);
            log.info("Found {} existing dates in DB", existingDates.size());
            log.info("Existing dates: {}", existingDates);
            
            // 2달 전부터 어제까지의 모든 날짜 생성
            Set<LocalDate> allDates = IntStream.rangeClosed(0, (int) twoMonthsAgo.until(yesterday).getDays())
                    .mapToObj(twoMonthsAgo::plusDays)
                    .collect(Collectors.toSet());
            log.info("Total dates to check: {}", allDates.size());
            
            // DB에 없는 날짜들 필터링
            Set<LocalDate> missingDates = allDates.stream()
                    .filter(date -> !existingDates.contains(date))
                    .collect(Collectors.toSet());
            log.info("Found {} missing dates", missingDates.size());
            log.info("Missing dates: {}", missingDates);
            
            if (missingDates.isEmpty()) {
                log.info("No missing weather data found between {} and {}", twoMonthsAgo, yesterday);
                return;
            }

            // 누락된 각 날짜에 대해 데이터 조회
            for (LocalDate date : missingDates) {
                String formattedDate = date.format(DateTimeFormatter.BASIC_ISO_DATE);
                try {
                    log.info("Fetching weather data for date: {}", formattedDate);
                    // 서울(108), 인천(112), 수원(119) 지점의 날씨 데이터 조회
                    weatherApiService.getWeatherData(formattedDate, formattedDate, "108,112,119");
                    log.info("Weather data successfully fetched for date: {}", formattedDate);
                    
                    // API 호출 간격 조절을 위한 짧은 대기
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("Error fetching weather data for date {}: {}", formattedDate, e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error in batch process: {}", e.getMessage(), e);
        }
    }
}
