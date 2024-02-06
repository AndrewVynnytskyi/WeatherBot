package org.example.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class WeatherForecastDto {
        private ArrayList<List> list;
        private City city;
        private final Map<String, String> emojis =getWeatherEmoji();

    private static Map<String, String> getWeatherEmoji() {
        Map<String, String> weatherDescriptionToEmojiMap = new HashMap<>();


        weatherDescriptionToEmojiMap.put("thunderstorm with light rain", "⛈️🌧️");
        weatherDescriptionToEmojiMap.put("thunderstorm with rain", "⛈️🌧️");
        weatherDescriptionToEmojiMap.put("thunderstorm with heavy rain", "⛈️🌧️💦");
        weatherDescriptionToEmojiMap.put("light thunderstorm", "⛈️⚡");
        weatherDescriptionToEmojiMap.put("thunderstorm", "⛈️⚡");
        weatherDescriptionToEmojiMap.put("heavy thunderstorm", "⛈️⚡💦");
        weatherDescriptionToEmojiMap.put("ragged thunderstorm", "⛈️⚡");
        weatherDescriptionToEmojiMap.put("thunderstorm with light drizzle", "⛈️🌧️💧");
        weatherDescriptionToEmojiMap.put("thunderstorm with drizzle", "⛈️🌧️💧");
        weatherDescriptionToEmojiMap.put("thunderstorm with heavy drizzle", "⛈️🌧️💧💦");

        weatherDescriptionToEmojiMap.put("light intensity drizzle", "🌧️💧");
        weatherDescriptionToEmojiMap.put("drizzle", "🌧️💦");
        weatherDescriptionToEmojiMap.put("heavy intensity drizzle", "🌧️💦💦");
        weatherDescriptionToEmojiMap.put("light intensity drizzle rain", "🌧️💧");
        weatherDescriptionToEmojiMap.put("drizzle rain", "🌧️💦");
        weatherDescriptionToEmojiMap.put("heavy intensity drizzle rain", "🌧️💦💦");
        weatherDescriptionToEmojiMap.put("shower rain and drizzle", "🌧️💦💧");
        weatherDescriptionToEmojiMap.put("heavy shower rain and drizzle", "🌧️💦💦💧");
        weatherDescriptionToEmojiMap.put("shower drizzle", "🌧️💦");

        weatherDescriptionToEmojiMap.put("light rain", "🌧️💦");
        weatherDescriptionToEmojiMap.put("moderate rain", "🌧️💦💦");
        weatherDescriptionToEmojiMap.put("heavy intensity rain", "🌧️💦💦💦");
        weatherDescriptionToEmojiMap.put("very heavy rain", "🌧️💦💦💦💦");
        weatherDescriptionToEmojiMap.put("extreme rain", "🌧️💦💦💦💦💦");
        weatherDescriptionToEmojiMap.put("freezing rain", "🌧️❄️💦");
        weatherDescriptionToEmojiMap.put("light intensity shower rain", "🌧️💦💧");
        weatherDescriptionToEmojiMap.put("shower rain", "🌧️💦💦💧");
        weatherDescriptionToEmojiMap.put("heavy intensity shower rain", "🌧️💦💦💦💧");
        weatherDescriptionToEmojiMap.put("ragged shower rain", "🌧️💦💧");

        weatherDescriptionToEmojiMap.put("light snow", "❄️💧");
        weatherDescriptionToEmojiMap.put("snow", "❄️💦");
        weatherDescriptionToEmojiMap.put("heavy snow", "❄️💦💦");
        weatherDescriptionToEmojiMap.put("sleet", "🌨️❄️💦");
        weatherDescriptionToEmojiMap.put("light shower sleet", "🌨️❄️💦💧");
        weatherDescriptionToEmojiMap.put("shower sleet", "🌨️❄️💦💦💧");
        weatherDescriptionToEmojiMap.put("light rain and snow", "🌨️💦❄️");
        weatherDescriptionToEmojiMap.put("rain and snow", "🌨️💦❄️💧");
        weatherDescriptionToEmojiMap.put("light shower snow", "🌨️💦💧");
        weatherDescriptionToEmojiMap.put("shower snow", "🌨️💦💧💦");
        weatherDescriptionToEmojiMap.put("heavy shower snow", "🌨️💦💦💧💦");

        weatherDescriptionToEmojiMap.put("mist", "🌫️");
        weatherDescriptionToEmojiMap.put("smoke", "🌫️");
        weatherDescriptionToEmojiMap.put("haze", "🌫️");
        weatherDescriptionToEmojiMap.put("sand/dust whirls", "🌪️");
        weatherDescriptionToEmojiMap.put("fog", "🌫️");
        weatherDescriptionToEmojiMap.put("sand", "🌪️");
        weatherDescriptionToEmojiMap.put("dust", "🌪️");
        weatherDescriptionToEmojiMap.put("volcanic ash", "🌋");
        weatherDescriptionToEmojiMap.put("squalls", "🌪️");
        weatherDescriptionToEmojiMap.put("tornado", "🌪️");

        weatherDescriptionToEmojiMap.put("clear sky", "☀️");
        weatherDescriptionToEmojiMap.put("few clouds", "⛅");
        weatherDescriptionToEmojiMap.put("scattered clouds", "🌥️");
        weatherDescriptionToEmojiMap.put("broken clouds", "☁️");
        weatherDescriptionToEmojiMap.put("overcast clouds", "☁️");


        return weatherDescriptionToEmojiMap;
    }



        private String getDirection(int deg) {
            return deg >= 337 || deg < 23 ? "North" :
                    deg < 68 ? "Northeast" :
                            deg < 113 ? "East" :
                                    deg < 158 ? "Southeast" :
                                            deg < 203 ? "South" :
                                                    deg < 248 ? "Southwest" :
                                                            deg < 293 ? "West" :
                                                                    "Northwest";
        }

        public ArrayList<String> toDataArrayD()
        {
        ArrayList<String> dataArr = new ArrayList<>();

        String data = "";
        for(List element: list )
        {

            if(!data.equals(element.dt_txt.substring(0, 10)))
            {
                var date = LocalDate.parse(element.dt_txt.substring(0, 10));
                String dayOfWeek = date.getDayOfWeek().toString();
                dayOfWeek = dayOfWeek.charAt(0) + dayOfWeek.substring(1, 3).toLowerCase();
                dataArr.add(date.getDayOfMonth() + " " + dayOfWeek) ;
                data = element.dt_txt.substring(0, 10);
            }


        }

        return dataArr;
        }
        public java.util.List<String>  toArrayD()
        {
            return list.stream().map(elementH ->
            {
                String stringStream = list.stream()
                        .filter(element -> (elementH.dt_txt.substring(0,10)
                                .equals(element.dt_txt.substring(0, 10))))
                        .map(element-> element.dt_txt.substring(11,16) + ": " +
                        capitalize(element.weather.getFirst().description) +
                        " " + emojis.get(element.weather.getFirst().description)+
                        ", "+ element.main.temp + "°C, "+
                        "Precipitation: "+ element.pop +"%, "+
                        getDirection(element.wind.deg)+" wind at "+
                        element.wind.speed +"m/s\n").distinct().collect(Collectors.joining());
               return stringStream.concat("\nYour location: " + city.name +"\nDate of forecast: " + elementH.dt_txt.substring(0,10));
            }).distinct().toList();
        }
}
class City{
    String name;
}




class List{

    Main_F main;
    ArrayList<Weather_F> weather;
    Wind_F wind;
    int visibility;
    double pop;
    Sys_F sys;
    String dt_txt;
}

class Main_F{
    double temp;
    double feels_like;
    double temp_min;
    double temp_max;
    int pressure;
    int humidity;


}



class Sys_F{
    String pod;
}

class Weather_F{
    String main;

    String description;


}

class Wind_F{
    double speed;
    int deg;


}