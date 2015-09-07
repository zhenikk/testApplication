package com.abz.yevhenii.testapplication.rest;

import com.abz.yevhenii.testapplication.rest.service.ApiService;

import retrofit.RestAdapter;

/**
 * Created by yevhenii
 */
public class RestClient {
    private static final String BASE_URL = "http://504080.com/";
    ApiService apiService;
    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(
                RestAdapter.LogLevel.FULL
        ).setEndpoint(BASE_URL).build();
        apiService = restAdapter.create(ApiService.class);
    }
    public ApiService getApiService(){
        return apiService;
    }
}
