package com.egypaytask.data.network;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  public static final String BASE_URL ="http://101.amrbdr.com/";// "http://151.236.222.252/mtc2/";

  private static ApiClient instance;

  private ApiInterface apiService;

  public ApiClient() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder() .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS).build();

    Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build();

    apiService = retrofit.create(ApiInterface.class);
  }

  public static ApiClient getInstance() {
    if (instance == null) {
      instance = new ApiClient();
    }
    return instance;
  }

  public ApiInterface getApiService() {
    return apiService;
  }
}
