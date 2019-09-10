package com.moshrouk.sofra.data.reset.restaurant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRestaurant {

    public static String BASE_URL = "http://ipda3-tech.com/sofra-v2/api/v2/restaurant/";
    public static Retrofit retrofit = null;

    public static Retrofit getRestaurant() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
