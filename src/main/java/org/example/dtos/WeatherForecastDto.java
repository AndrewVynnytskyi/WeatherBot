package org.example.dtos;

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


        public ArrayList<String>  toArray()
        {
            ArrayList<String> forecast = new ArrayList<>();


            for(List element: list )
            {
                String buff = "_________________________________\n"+"Weather: " + element.weather.getFirst().main +
                        "\nDescription of the weather: " + element.weather.getFirst().description +
                        "\nThe temperature: "+ element.main.temp +
                        "\nFeels like: " + element.main.feels_like +
                        "\nHumidity: " + element.main.humidity +
                        "\nPressure: " + element.main.pressure +
                        "\nProbability of precipitation: "+ element.pop +
                        "\nWind speed: " + element.wind.speed +
                        "\nDirection: " + getDirection(element.wind.deg) +
                        "\nPart of day: " + (getPartOfDay(element.sys.pod)) +
                        "\nDate of Forecast: " + element.dt_txt +
                        "\nYour location: " + city.name +
                        "\n_________________________________\n";


                forecast.add(buff);
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