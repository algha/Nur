package com.algha.nur.model;

import org.json.JSONException;
import org.json.JSONObject;


public class Category {
	public String Id;
	public String Name;
	
	public Category(JSONObject object){
		try {
			Id = object.getString("Id");
			Name = object.getString("Name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String GetId(){
		return Id;
	}
	
	public String GetName(){
		return Name;
	}
		
}
