package org.example.dtos;

import java.util.ArrayList;

public record WeatherDto(
         ArrayList<Weather> weather,
         Main main,
         int visibility,
         Wind wind,
         Sys sys,
         String name
) {
    private String getDirection()
    {
        return wind.deg >= 337 || wind.deg < 23 ? "North" :
                wind.deg < 68 ? "Northeast" :
                        wind.deg < 113 ? "East" :
                                wind.deg < 158 ? "Southeast" :
                                        wind.deg < 203 ? "South" :
                                                wind.deg < 248 ? "Southwest" :
                                                        wind.deg < 293 ? "West" :
                                                                "Northwest";
    }
    @Override
    public String toString()
    {
        return ("Weather: " + weather.getFirst().main +
                "\nDescription of the weather: " + weather.getFirst().description +
                "\nThe temperature: "+ main.temp +
                "\nFeels like: " +main.feels_like +
                "\nHumidity: " + main.humidity +
                "\nPressure: " + main.pressure +
                "\nVisibility: " + visibility/1000 + " km" +
                "\nWind speed: " + wind.speed +
                "\nDirection: " + getDirection() +
                "\nSunrise today:" + new java.util.Date(sys.sunrise*1000) +
                "\nSunset today:" + new java.util.Date(sys.sunset*1000) +
                "\nThe nearest city or village: " + name);
    }

}

class Weather
{
    String main;
    String description;

}

class Main
{
    double temp;
    double feels_like;
    int pressure;
    int humidity;

}

class Wind
{
    double speed;

    int deg;
}
class Sys
{
    long sunrise;
    long sunset;
}


