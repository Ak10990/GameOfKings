package com.android.thoughtworks.helpers.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BattleApiClient {

    /**
     * Api EndPoints
     */
    private static final String API_HOMEPAGE = "http://starlord.hackerearth.com";

    private BattleApiService apiService;

    public BattleApiClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_HOMEPAGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(BattleApiService.class);
    }

    public BattleApiService getApiService() {
        return apiService;
    }

}