package org.example.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
    private static Retrofit HttpClient1 = null;
    private static Retrofit HttpClient2 = null;
    private static String Url = "https://api.openweathermap.org/data/2.5/";
    private static String UrlF = "https://api.openweathermap.org/data/2.5/";
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
}
