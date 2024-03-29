package org.example.network;

import org.example.dtos.WeatherForecastDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastQuerries {
    @GET("forecast?")
    Call<WeatherForecastDto> getForecst(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String appid,
            @Query("units") String units);

}
