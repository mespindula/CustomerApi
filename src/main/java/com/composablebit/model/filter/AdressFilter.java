package com.composablebit.model.filter;

import java.util.List;

public class AdressFilter {
	
	private String state;
	
	private String city;
	
	List<Long> customerId;

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public List<Long> getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(List<Long> customerId) {
		this.customerId = customerId;
	}
	
}
