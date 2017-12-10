package com.algha.nur.model;

import java.io.Serializable;

public class Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8113974971013644056L;
	public String Id;
	public String Poster;
	public String Title;
	public String Content;
	public String Click;
	public String Created_at;
	
	public Info(String id,String poster,String title,String content,String click,String created_at){
		Id = id;
		Poster = poster;
		Title = title;
		Content = content;
		Click = click;
		Created_at = created_at;
	}
	
	public Info() {
		// TODO Auto-generated constructor stub
	}

	public String GetId() {
		return Id;
	}
	
	public String GetPoster() {
		return Poster;
	}
	public String GetTitle() {
		return Title;
	}
	public String GetContent() {
		return Content;
	}
	public String GetClick() {
		return Click;
	}
	public String GetCreated_at() {
		return Created_at;
	}
}
