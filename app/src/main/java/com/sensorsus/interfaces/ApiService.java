package com.sensorsus.interfaces;

import com.google.gson.JsonObject;
import com.sensorsus.services.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {


    @GET("/estabelecimentos")
    Call<JsonObject> getData();


    @GET("/estabelecimentos/{id}")
    Call<JsonObject> getEstabaelecimentoAvaliacao(@Path(value = "id", encoded = true) String from);
}
