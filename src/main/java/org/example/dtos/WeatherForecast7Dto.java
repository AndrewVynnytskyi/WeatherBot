package org.example.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.apache.commons.lang3.StringUtils.capitalize;

public class WeatherForecast7Dto {

    private Daily daily;
    private Hourly hourly;

    private final Map<Integer, String> emojis = getWeatherEmoji();
    @Override
    public String toString()
    {
        String forecast = "";
        for(DatumD element : hourly.data)
        {
            String buff = element.date.substring(11, 16) + ": " +
                    capitalize(element.summary) +" " + emojis.get(element.icon) +
                    ", "+ element.temperature + "°C, "+
                    (element.precipitation.type.equals("none")? "No precipitation": capitalize(element.precipitation.type)) +", "+
                    element.wind.dir+" wind at "+
                    element.wind.speed +"m/s\n";
            forecast = forecast.concat(buff);
        }
        return forecast;
    }

    private static  Map<Integer, String>  getWeatherEmoji() {

        Map<Integer, String> weatherCodeToEmojiMap = new HashMap<>();

        // Mapping weather codes to emojis
        weatherCodeToEmojiMap.put(2, "☀️"); // Sunny
        weatherCodeToEmojiMap.put(3, "🌤️"); // Mostly sunny
        weatherCodeToEmojiMap.put(4, "⛅"); // Partly sunny
        weatherCodeToEmojiMap.put(5, "🌥️"); // Mostly cloudy
        weatherCodeToEmojiMap.put(6, "☁️"); // Cloudy
        weatherCodeToEmojiMap.put(7, "☁️"); // Overcast
        weatherCodeToEmojiMap.put(8, "☁️"); // Overcast with low clouds
        weatherCodeToEmojiMap.put(9, "🌫️"); // Fog
        weatherCodeToEmojiMap.put(10, "🌧️"); // Light rain
        weatherCodeToEmojiMap.put(11, "🌧️☔"); // Rain
        weatherCodeToEmojiMap.put(12, "🌧️?"); // Possible rain
        weatherCodeToEmojiMap.put(13, "🌧️🚿"); // Rain shower
        weatherCodeToEmojiMap.put(14, "⛈️🌩️"); // Thunderstorm
        weatherCodeToEmojiMap.put(15, "⛈️🌩️🏡"); // Local thunderstorms
        weatherCodeToEmojiMap.put(16, "❄️🌨️"); // Light snow
        weatherCodeToEmojiMap.put(17, "❄️🌨️"); // Snow
        weatherCodeToEmojiMap.put(18, "❄️?"); // Possible snow
        weatherCodeToEmojiMap.put(19, "❄️🚿"); // Snow shower
        weatherCodeToEmojiMap.put(20, "🌧️❄️"); // Rain and snow
        weatherCodeToEmojiMap.put(21, "🌧️?❄️"); // Possible rain and snow
        weatherCodeToEmojiMap.put(22, "🌧️❄️"); // Rain and snow
        weatherCodeToEmojiMap.put(23, "❄️🌧️"); // Freezing rain
        weatherCodeToEmojiMap.put(24, "❄️?🌧️"); // Possible freezing rain
        weatherCodeToEmojiMap.put(25, "❄️🌨️💧"); // Hail
        weatherCodeToEmojiMap.put(26, "🌙"); // Clear (night)
        weatherCodeToEmojiMap.put(27, "🌙☁️"); // Mostly clear (night)
        weatherCodeToEmojiMap.put(28, "🌙⛅"); // Partly clear (night)
        weatherCodeToEmojiMap.put(29, "🌙🌥️"); // Mostly cloudy (night)
        weatherCodeToEmojiMap.put(30, "🌙☁️"); // Cloudy (night)
        weatherCodeToEmojiMap.put(31, "🌙☁️"); // Overcast with low clouds (night)
        weatherCodeToEmojiMap.put(32, "🌙🌧️🚿"); // Rain shower (night)
        weatherCodeToEmojiMap.put(33, "🌙⛈️🌩️🏡"); // Local thunderstorms (night)
        weatherCodeToEmojiMap.put(34, "🌙❄️🚿"); // Snow shower (night)
        weatherCodeToEmojiMap.put(35, "🌙🌧️❄️"); // Rain and snow (night)
        weatherCodeToEmojiMap.put(36, "🌙❄️?🚿"); // Possible freezing rain (night)

        return weatherCodeToEmojiMap;
    }

    public String toString7()
    {
        String forecast = "";


        for(Datum element: daily.data )
        {
            String buff = "_________________________________\n"+
                    "Weather: " + element.summary +
                    "\nThe temperature: "+ element.all_day.temperature +
                    "\nMax temperature: " + element.all_day.temperature_max +
                    "\nMin temperature: " + element.all_day.temperature_min +
                    "\nProbability of precipitation: "+ element.all_day.precipitation.total +
                    "\nPrecipitation type: " + element.all_day.precipitation.type +
                    "\nWind speed: " + element.all_day.wind.speed +
                    "\nDirection: " + element.all_day.wind.dir +
                    "\nDate of Forecast: " + element.day +
                    "\n_________________________________\n";


            forecast = forecast.concat(buff);
        }


        return forecast;
    }

    public ArrayList<String> toDataArray()
    {
        ArrayList<String> dataArr = new ArrayList<>();


        for(Datum element: daily.data )
        {
            var date = LocalDate.parse(element.day);
            String dayOfWeek = date.getDayOfWeek().toString();
            dayOfWeek = dayOfWeek.charAt(0) + dayOfWeek.substring(1, 3).toLowerCase();
            dataArr.add(date.getDayOfMonth() + " " + dayOfWeek) ;
        }

        return dataArr;
    }


    public ArrayList<String> toArray()
    {
        ArrayList<String> forecastArr = new ArrayList<>();


        for(Datum element: daily.data )
        {
            String buff = "_________________________________\n"+
                    "Weather: " + element.summary +" " + emojis.get(element.icon) +
                    "\nTemperature: "+ element.all_day.temperature + "°C, Max: " +
                     element.all_day.temperature_max + "°C, Min: "+ element.all_day.temperature_min + "°C\n"+
                    "Precipitation: "+ element.all_day.precipitation.total +" mm, " + capitalize(element.all_day.precipitation.type)+
                    "\nWind: " + element.all_day.wind.speed +"m/s from " + element.all_day.wind.dir +
                    "\nDate of Forecast: " + element.day +
                    "\n_________________________________\n";


            forecastArr.add(buff);
        }
        return forecastArr;
    }
}


class Hourly{
    public ArrayList<DatumD> data;
}


class DatumD{
    public String date;
    public String summary;

    public int icon;
    public double temperature;
    public WindF wind;
    public Precipitation precipitation;
}
class AllDay{

    public double temperature;
    public double temperature_min;
    public double temperature_max;
    public WindF wind;

    public Precipitation precipitation;
}



class Daily{
    public ArrayList<Datum> data;
}

class Datum{
    public String day;
    int icon;
    public String summary;
    public AllDay all_day;
}

class Precipitation{
    public double total;
    public String type;
}



class WindF{
    public double speed;
    public String dir;
}

