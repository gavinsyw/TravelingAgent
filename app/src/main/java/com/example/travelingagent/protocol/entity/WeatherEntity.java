package com.example.travelingagent.protocol.entity;

import java.util.List;

public class WeatherEntity {
    public String message;
    public String code;
    public List<Value> value;

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
