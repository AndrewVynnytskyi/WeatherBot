package org.example.network;

import org.example.dtos.WeatherDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherQueries {


    @GET("weather?")
    Call<WeatherDto> getWeather(@Query("lat") double lat,
                                @Query("lon") double lon,
                                @Query("appid") String appid,
                                @Query("units") String units);


}
