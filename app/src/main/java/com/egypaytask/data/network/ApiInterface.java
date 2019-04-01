package com.egypaytask.data.network;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

  @POST(" ")
  Call<Void> sendInformation(@Body HashMap<Integer,String> informationTable);


}
