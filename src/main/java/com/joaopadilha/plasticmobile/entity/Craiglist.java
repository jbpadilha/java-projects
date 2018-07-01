package com.joaopadilha.plasticmobile.entity;


public class Craiglist {
	
    private String title; 

	private String date;

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Craiglist{"
			   +"title:'"+ getTitle()+"'"
			   +", date:'"+ getDate()+"'"
			   +"}";
	}
}
