package com.kunal.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse{
    public Current current;

    @Getter
    @Setter
    public class Current{
        public double temp_c;
        public double feelslike_c;
    }
}

