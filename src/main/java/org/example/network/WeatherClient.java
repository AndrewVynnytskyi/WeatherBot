package org.example.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
    private static Retrofit HttpClient = null;
    private static String Url = "https://api.openweathermap.org/data/2.5/";

    public WeatherQueries getWeatherClient() {

         HttpClient = new Retrofit.Builder()
                 .baseUrl(Url)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        return HttpClient.create(WeatherQueries.class);
    }
}
