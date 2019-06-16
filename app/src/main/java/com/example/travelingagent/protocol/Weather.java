package com.example.travelingagent.protocol;

import java.util.List;

public class Weather {
    public String message;
    public String code;
    public List<Value> value;

//    public Weather(String message, String code) {
//        this.message = message;
//        this.code = code;
//    }

    public static class Value {
        public String city;
        public int cityid;
        public List<WeatherInfo> weathers;
        public List<ForecastInfo> indexes;

        public static class ForecastInfo {
            public String name;
            public String level;
            public String content;
        }

        public static class WeatherInfo {
            public String date;
            public String weather;
            public String temp_day_c;
            public String temp_night_c;
        }
    }
}
