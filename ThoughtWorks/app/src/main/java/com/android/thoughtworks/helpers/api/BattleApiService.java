package com.android.thoughtworks.helpers.api;

import com.android.thoughtworks.model.Battle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BattleApiService {

    @GET("/gotjson")
    Call<List<Battle>> listBattles();
}