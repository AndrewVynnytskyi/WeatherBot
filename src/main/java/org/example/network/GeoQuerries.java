package org.example.network;

import org.example.dtos.GeocodeDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeoQuerries {

    @GET("geocoding/{city}.json?")
    Call<GeocodeDto> getGeo(
            @Path("city") String name,
            @Query("autocomplete") boolean autocomplete,
            @Query("fuzzyMatch") boolean fuzzyMatch,
            @Query("limit") int limit,
            @Query("key") String key
    );
}
