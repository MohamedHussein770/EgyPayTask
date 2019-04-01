package com.egypaytask;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import com.egypaytask.data.models.Field;
import com.egypaytask.data.enums.Type;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity
    extends AppCompatActivity implements FormContract.View {

  List <Field> fields;
  FormContract.Presenter presenter;
  ProgressBar progressBar;
  ScrollView scrollView;
  LinearLayout rootLayout;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    presenter = new FormPresenter(this);

    InputStream raw =  getResources().openRawResource(R.raw.data);

    fields =presenter.getListOfFieldsFromJsonFile(raw);







    Collections.sort(fields, new Comparator<Field>(){
      @Override
      public int compare(Field field, Field t1) {
        return Integer.valueOf(field.getSort()).compareTo(Integer.valueOf(t1.getSort()));
      }
    });


    createViews();







  }



  @SuppressLint("ClickableViewAccessibility")
  void createViews(){


    Display display = getWindowManager().getDefaultDisplay();
    int screenWidth = display.getWidth();

    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100,1);


    LinearLayout.LayoutParams params2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
    params2.gravity = Gravity.RIGHT;

    LinearLayout.LayoutParams params3= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);


    ScrollView.LayoutParams scrollParams = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);
    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100,1);
    buttonParams.setMargins(0,30,0,0);

    scrollView = new ScrollView(this);
    scrollView.setLayoutParams(scrollParams);





    rootLayout = new LinearLayout(this);
    rootLayout.setGravity(Gravity.CENTER);
    rootLayout.setPadding(20,20,20,20);
    rootLayout.setOrientation(LinearLayout.VERTICAL);


    for(Field field : fields){


      LinearLayout inputLayout = new LinearLayout(this);
      inputLayout.setGravity(Gravity.RIGHT);
      inputLayout.setPadding(5,5,5,5);
      inputLayout.setOrientation(LinearLayout.HORIZONTAL);
      inputLayout.setLayoutParams(params1);
      TextView tv_name = new TextView(this);
      tv_name.setText(field.getName());
      tv_name.setLayoutParams(params2);
      tv_name.setGravity(Gravity.CENTER |Gravity.END);
      tv_name.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
      tv_name.setPaddingRelative(0,5,0,0);
      tv_name.setWidth(screenWidth/2);
      tv_name.setTextSize(16);
      tv_name.setTextColor(Color.BLACK);
      switch (field.getType()){

        case Type.STRING :
          EditText et_string = new EditText(this);
          et_string.setId(field.getId());
          et_string.setTag(field);
          et_string.setInputType(InputType.TYPE_CLASS_TEXT);
          et_string.setLayoutParams(params2);
          et_string.setGravity(Gravity.CENTER |Gravity.END);
          et_string.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
          et_string.setWidth(screenWidth/2);
          inputLayout.addView(et_string);
          inputLayout.addView(tv_name);
          break;

        case Type.NUMBER :
          EditText et_number = new EditText(this);
          et_number.setId(field.getId());
          et_number.setTag(field);
          et_number.setInputType(InputType.TYPE_CLASS_NUMBER);
          et_number.setLayoutParams(params2);
          et_number.setGravity(Gravity.CENTER |Gravity.END);
          et_number.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
          et_number.setWidth(screenWidth/2);

          inputLayout.addView(et_number);
          inputLayout.addView(tv_name);
          break;

        case Type.TEXT_AREA :
          EditText et_textArea = new EditText(this);
          et_textArea.setId(field.getId());
          et_textArea.setTag(field);
          et_textArea.setLayoutParams(params2);
          et_textArea.setGravity(Gravity.CENTER |Gravity.END);
          et_textArea.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
          et_textArea.setSingleLine(false);
          et_textArea.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
          et_textArea.setBackgroundColor(Color.LTGRAY);
          et_textArea.setWidth(screenWidth/2);
          et_textArea.setHeight(50);
          et_textArea.setVerticalScrollBarEnabled(true);
          inputLayout.addView(et_textArea);
          inputLayout.addView(tv_name);

          break;


        case Type.DATE :
          final EditText et_date = new EditText(this);
          et_date.setId(field.getId());
          et_date.setTag(field);
          et_date.setHint("yyyy-mm-dd");
          et_date.setHintTextColor(Color.LTGRAY);
          et_date.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
          et_date.setLayoutParams(params2);
          et_date.setGravity(Gravity.CENTER |Gravity.END);
          et_date.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
          et_date.setWidth(screenWidth/2);
          et_date.setFocusable(false);
          et_date.setFocusableInTouchMode(false);

          et_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
              switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                  createDatePicker(et_date);
                  break;
              }
              return false;
            }
          });
          inputLayout.addView(et_date);
          inputLayout.addView(tv_name);


          break;

        case Type.SELECT :
          Spinner sp_select = new Spinner(this);
          sp_select.setId(field.getId());
          sp_select.setTag(field);
          sp_select.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
          LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(params3);
          lp.gravity= Gravity.RIGHT;
          sp_select.setLayoutParams(lp);
          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, presenter.getSpinnerList(field));
          sp_select.setAdapter(spinnerArrayAdapter);
          inputLayout.addView(sp_select);
          inputLayout.addView(tv_name);

          break;


      }


      inputLayout.setPaddingRelative(20,5,20,5);

      rootLayout.addView(inputLayout);

    }

    Button button = new Button(this);
    button.setText("Send");
    button.setLayoutParams(buttonParams);
    button.setMaxWidth((int)  0.80 *screenWidth);
    progressBar = new ProgressBar(this);
    progressBar.setVisibility(View.GONE);

    rootLayout.addView(progressBar);
    rootLayout.addView(button);
    scrollView.addView(rootLayout);
    setContentView(scrollView);


    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        sendRequest(rootLayout);

      }
    });
  }






  void sendRequest(LinearLayout linearLayout){

    HashMap<Integer,String> request= new HashMap<>();
    for (Field field : fields) {

      if (!field.getType().equals(Type.SELECT)) {

        EditText editText = (EditText) linearLayout.findViewById(field.getId());

        if(editText.getText().toString().isEmpty() && field.isRquired()){

          Toast.makeText(this," يجب إدخال "+field.getName(), Toast.LENGTH_SHORT).show();

          return;
        }else{

        request.put(field.getId(),editText.getText().toString());


        }

      } else{

        Spinner spinner = (Spinner) linearLayout.findViewById(field.getId());

        String selectedItem = spinner.getSelectedItem().toString();

        if(selectedItem.isEmpty() &&  field.isRquired() ){

          Toast.makeText(this," يجب إدخال "+field.getName(), Toast.LENGTH_SHORT).show();

          return;

        }else{

          request.put(field.getId(),selectedItem);

        }

      }

    }


    presenter.sendInformation(request);


  }


  void createDatePicker(final EditText et_date){
    final Calendar myCalendar = Calendar.getInstance();
    et_date.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        new DatePickerDialog(FormActivity.this, new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            String m,d;
            month = month+1;
            if(month<10)
              m = "0"+month;
            else
              m = month+"";

            if(day<10)
              d= "0"+day;
            else
              d= ""+day;
            et_date.setText(year+"-"+m+"-"+d);
          }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

  }

  @Override
  protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public void showLoader() {

    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoader() {

    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void showMessage(String message) {

    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
