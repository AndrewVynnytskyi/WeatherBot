package org.example.network;

import org.example.dtos.WeatherForecast7Dto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Forecast7Querries {
    @GET("point?")
    Call<WeatherForecast7Dto> getForecast7(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("sections") String sections,
            @Query("timezone") String timezone,
            @Query("units") String units,
            @Query("key") String key

    );
}
