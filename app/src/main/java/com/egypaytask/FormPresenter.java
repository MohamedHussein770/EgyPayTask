package com.egypaytask;

import android.support.annotation.NonNull;
import com.egypaytask.data.models.Field;
import com.egypaytask.data.models.Multiple;
import com.egypaytask.data.network.ApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPresenter implements FormContract.Presenter {



  private FormContract.View view;
 private Call call;


  public FormPresenter(FormContract.View view) {
    this.view = view;
  }



  @Override
  public List<Field> getListOfFieldsFromJsonFile(InputStream inputStream){

    Reader rd = new BufferedReader(new InputStreamReader(inputStream));
    Gson gson = new Gson();
    Type type = new TypeToken<List<Field>>(){}.getType();
    List<Field>feilds = gson.fromJson(rd, type);

    return  feilds;
  }

  @Override
  public ArrayList<String> getSpinnerList(Field field){

    ArrayList<String> spinnerList = new ArrayList<>();

    if(field.getDefault_value()!= null)
      spinnerList.add(field.getDefault_value());


    for(Multiple multiple : field.getMultiples()){


      spinnerList.add(multiple.getName());

    }

    return spinnerList;
  }


  @Override
  public void sendInformation(final HashMap<Integer,String> request){



    call = ApiClient.getInstance().getApiService().sendInformation(request);
    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {



          view.showMessage(response.message());

          view.hideLoader();
      }

      @Override
      public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
        view.showMessage(t.getMessage());
        view.hideLoader();
      }
    });




  }


  @Override
  public void onDestroy() {
    if (call != null) {
      call.cancel();
    }
  }
}
