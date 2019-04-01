package com.egypaytask.data.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Field { // implements Comparable<com.egypaytask.data.models.Field> {



  private int id;
  private  String name;
  private  String required;
  private  String type;
  private  String default_value;
  private  String multiple;
  private  String sort;



  public Field(int id, String name, String required, String type, String default_value, String multiple, String sort) {
    this.id = id;
    this.name = name;
    this.required = required;
    this.type = type;
    this.default_value = default_value;
    this.multiple = multiple;
    this.sort = sort;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getRequired() {
    return required;
  }

  public String getType() {
    return type;
  }

  public String getDefault_value() {
    return default_value;
  }

  public String getMultiple() {
    return multiple;
  }

  public String getSort() {
    return sort;
  }

  public void setMultiple(String multiple) {
    this.multiple = multiple;
  }


  public List<Multiple> getMultiples (){

    Gson gson = new Gson();
    Type type = new TypeToken<List<Multiple>>(){}.getType();
    List <Multiple> multiples = gson.fromJson(this.multiple, type);

    return  multiples;
  }


  public boolean isRquired(){

    if(required.equals("yes"))
      return true;
    else
      return false;

  }
}
