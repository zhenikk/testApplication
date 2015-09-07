package com.abz.yevhenii.testapplication.rest.service;

import com.abz.yevhenii.testapplication.rest.model.Data;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by yevhenii
 */
public interface ApiService {
   // @Header("Get Help data")
    @GET("/api/v1/help")
    void getHelpData(@Header("Authorization") String token, Callback<Data> data);

}
