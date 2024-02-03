package org.example.dtos;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class WeatherForecast7Dto {

    private Daily daily;

    @Override
    public String toString()
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


            forecastArr.add(buff);
        }
        return forecastArr;
    }
}

class AllDay{

    public double temperature;
    public double temperature_min;
    public double temperature_max;
    public WindF wind;
    public CloudCover cloud_cover;
    public Precipitation precipitation;
}

class CloudCover{
    public int total;
}

class Daily{
    public ArrayList<Datum> data;
}

class Datum{
    public String day;
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

