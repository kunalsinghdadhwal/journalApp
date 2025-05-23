package com.kunal.journalApp.service;

import com.kunal.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "d2616a01e0cb4c86a36151452252305";

    @Autowired
    private RestTemplate restTemplate;

    private static final String API = "http://api.weatherapi.com/v1/current.json?key=KEY&q=CITY";

    public WeatherResponse getWeather(String city) {
        String finalApi = API.replace("CITY", city).replace("KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
