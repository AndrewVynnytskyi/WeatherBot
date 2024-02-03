package org.example.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class WeatherForecastDto {
        private ArrayList<List> list;
        private City city;

        private String getDirection(int deg) {
            return (deg >= 337 || deg < 23) ? "North" :
                    (deg >= 23 && deg < 68) ? "Northeast" :
                            (deg >= 68 && deg < 113) ? "East" :
                                    (deg >= 113 && deg < 158) ? "Southeast" :
                                            (deg >= 158 && deg < 203) ? "South" :
                                                    (deg >= 203 && deg < 248) ? "Southwest" :
                                                            (deg >= 248 && deg < 293) ? "West" :
                                                                    "Northwest";
        }
        private String getPartOfDay(String part)
        {
            return((Objects.equals(part, "d"))?"day":"night");
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
        public ArrayList<String>  toArrayD()
        {
            ArrayList<String> forecast = new ArrayList<>();
            String forecastString = "";
            String data = "";
            for(List element: list )
            {
                String buff;
                if(data.equals(element.dt_txt.substring(0, 10)))
                {
                     buff = element.dt_txt.substring(11,16) + " " +
                            element.weather.getFirst().description +
                            " "+ element.main.temp + "°C "+
                            "precipitation: "+ element.pop +" "+
                            getDirection(element.wind.deg)+" "+
                            element.wind.speed +"m/s\n";
                }
                else
                {
                    if(list.indexOf(element) != 0)
                    {
                        forecastString = forecastString.concat("\nYour location: " + city.name +"\nDate of forecast: " + data);
                        forecast.add(forecastString);
                    }
                    data = element.dt_txt.substring(0, 10);
                    buff = element.dt_txt.substring(11,16) + " " +
                            element.weather.getFirst().description +
                            " "+ element.main.temp + "°C "+
                            "precipitation: "+ element.pop +" "+
                            getDirection(element.wind.deg)+" "+
                            element.wind.speed +"m/s\n";
                    forecastString = "";
                }
                forecastString = forecastString.concat(buff);

            }
            return forecast;
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