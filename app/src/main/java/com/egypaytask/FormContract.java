package com.egypaytask;

import com.egypaytask.data.models.Field;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface FormContract {


   interface View{

     void showLoader();

     void hideLoader();
     void showMessage(String message);

  }

   interface Presenter{


    List<Field> getListOfFieldsFromJsonFile(InputStream inputStream);
    ArrayList<String> getSpinnerList(Field field);
    void sendInformation(HashMap<Integer,String> request);
    void onDestroy();
  }

}
