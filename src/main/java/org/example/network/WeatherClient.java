package org.example.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
    private static Retrofit HttpClient1 = null;
    private static Retrofit HttpClient2 = null;
    private static Retrofit HttpClient3 = null;
    private static Retrofit HttpClient4 = null;
    private static final String Url = "https://api.openweathermap.org/data/2.5/";
    private static final String UrlF = "https://api.openweathermap.org/data/2.5/";
    private  static final String Url_F = "https://www.meteosource.com/api/v1/free/";
    private static final String UrlG = "https://api.maptiler.com/";

    public WeatherQueries getWeatherClient() {

         HttpClient1 = new Retrofit.Builder()
                 .baseUrl(Url)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        return HttpClient1.create(WeatherQueries.class);
    }

    public ForecastQuerries getForecastClient()
    {
        HttpClient2 = new Retrofit.Builder()
                .baseUrl(UrlF)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return HttpClient2.create(ForecastQuerries.class);
    }

    public Forecast7Querries getForecast7Client()
    {
        HttpClient3 = new  Retrofit.Builder()
                .baseUrl(Url_F)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return HttpClient3.create(Forecast7Querries.class);
    }
    public GeoQuerries getCityCoord()
    {
        HttpClient4 = new Retrofit.Builder()
                .baseUrl(UrlG)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return HttpClient4.create(GeoQuerries.class);
    }
}
